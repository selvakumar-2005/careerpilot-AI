package com.careerpilot.ai;

import com.careerpilot.config.GroqProperties;
import com.careerpilot.exception.ai.GeminiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * HTTP client for Groq API (OpenAI-compatible format).
 * Primary AI provider with 100k tokens/day free tier.
 */
@Component
public class GroqHttpClient {

    private static final Logger log = LoggerFactory.getLogger(GroqHttpClient.class);

    private final GroqProperties props;
    private final ObjectMapper objectMapper;

    public GroqHttpClient(GroqProperties props) {
        this.props = props;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a text prompt to Groq API and returns the text response.
     */
    public String generate(String prompt) {
        validateApiKey();

        String url = props.buildEndpointUrl();
        String requestBody = buildRequestBody(prompt);
        int attempts = 0;
        int maxRetries = props.getMaxRetries();

        while (true) {
            attempts++;
            try {
                String raw = executePost(url, requestBody);
                return parseResponse(raw);
            } catch (GeminiException ex) {
                if (attempts <= maxRetries && ex.isRetryable()) {
                    log.warn("Groq transient error (attempt {}/{}): {}", attempts, maxRetries + 1, ex.getMessage());
                    sleep(props.getRetryDelayMs() * (long) attempts);
                } else {
                    throw ex;
                }
            }
        }
    }

    private void validateApiKey() {
        String key = props.getApiKey();
        if (key == null || key.isBlank() || key.equals("gsk_YOUR_GROQ_API_KEY_HERE")) {
            throw new GeminiException(
                "Groq API key is not configured. Set groq.api-key in application.properties. Get free key from https://console.groq.com",
                "INVALID_API_KEY", false);
        }
    }

    /**
     * Constructs OpenAI-compatible request body for Groq
     */
    private String buildRequestBody(String prompt) {
        try {
            ObjectNode root = objectMapper.createObjectNode();
            
            root.put("model", props.getModel());
            
            // messages array (OpenAI format)
            ArrayNode messages = root.putArray("messages");
            ObjectNode message = messages.addObject();
            message.put("role", "user");
            message.put("content", prompt);
            
            root.put("temperature", props.getTemperature());
            root.put("max_tokens", props.getMaxOutputTokens());

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            throw new GeminiException("Failed to build Groq request body: " + e.getMessage(),
                    "BUILD_ERROR", false);
        }
    }

    private String executePost(String url, String body) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.of(props.getConnectTimeoutMs(), TimeUnit.MILLISECONDS))
                .setResponseTimeout(Timeout.of(props.getReadTimeoutMs(), TimeUnit.MILLISECONDS))
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build()) {

            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + props.getApiKey());
            post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));

            log.debug("Groq API Request URL: {}", url);
            log.debug("Groq API Request Body: {}", body);

            return httpClient.execute(post, response -> {
                int statusCode = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                log.debug("Groq API status: {}", statusCode);
                log.debug("Groq API Response Body: {}", responseBody);

                if (statusCode == 200) {
                    return responseBody;
                }
                if (statusCode == 400) {
                    // Check if it's a model deprecation or bad request
                    if (responseBody.contains("decommissioned") || responseBody.contains("deprecated")) {
                        throw new GeminiException(
                            "Groq model has been deprecated. Please update the model in application.properties. See: https://console.groq.com/docs/deprecations",
                            "MODEL_DEPRECATED", false);
                    }
                    throw new GeminiException(
                        "Groq rejected the request (400). Check your prompt or API key. Response: " + responseBody,
                        "INVALID_API_KEY", false);
                }
                if (statusCode == 401) {
                    throw new GeminiException(
                        "Groq authentication failed (401). Check your API key.",
                        "INVALID_API_KEY", false);
                }
                if (statusCode == 429) {
                    throw new GeminiException(
                        "Groq rate limit exceeded (429). Please wait.",
                        "RATE_LIMIT", true);
                }
                if (statusCode >= 500) {
                    throw new GeminiException(
                        "Groq server error (" + statusCode + "). Retrying...",
                        "HTTP_ERROR", true);
                }
                throw new GeminiException(
                    "Unexpected Groq HTTP status: " + statusCode,
                    "HTTP_ERROR", false);
            });

        } catch (GeminiException ex) {
            throw ex;
        } catch (SocketTimeoutException ex) {
            throw new GeminiException(
                "Groq API timed out. Check connection or increase groq.read-timeout-ms.",
                "TIMEOUT", true);
        } catch (IOException ex) {
            throw new GeminiException(
                "Network error: " + ex.getMessage(),
                "NETWORK_ERROR", true);
        }
    }

    /**
     * Extracts text from OpenAI-compatible response:
     * choices[0].message.content
     */
    private String parseResponse(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);

            if (root.has("error")) {
                JsonNode error = root.get("error");
                String message = error.path("message").asText("Unknown Groq error");
                throw new GeminiException("Groq API error: " + message,
                        "HTTP_ERROR", false);
            }

            JsonNode choices = root.path("choices");
            if (choices.isMissingNode() || !choices.isArray() || choices.isEmpty()) {
                throw new GeminiException(
                    "Groq returned no choices in response.",
                    "EMPTY_RESPONSE", false);
            }

            String text = choices.get(0)
                    .path("message")
                    .path("content")
                    .asText("");

            if (text.isBlank()) {
                throw new GeminiException(
                    "Groq returned empty response.",
                    "EMPTY_RESPONSE", false);
            }

            return text.trim();

        } catch (GeminiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GeminiException(
                "Failed to parse Groq response: " + ex.getMessage(),
                "PARSE_ERROR", false);
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
