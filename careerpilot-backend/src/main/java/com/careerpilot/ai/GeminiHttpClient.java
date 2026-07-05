package com.careerpilot.ai;

import com.careerpilot.config.GeminiProperties;
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
 * HTTP client for Google Gemini API.
 * Used as fallback provider when Groq rate limit is exceeded.
 */
@Component
public class GeminiHttpClient {

    private static final Logger log = LoggerFactory.getLogger(GeminiHttpClient.class);

    private final GeminiProperties props;
    private final ObjectMapper objectMapper;

    public GeminiHttpClient(GeminiProperties props) {
        this.props = props;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a text prompt to Gemini API and returns the text response.
     */
    public String generate(String prompt) {
        validateApiKey();

        String url = props.buildEndpointUrl() + "?key=" + props.getApiKey();
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
                    log.warn("Gemini transient error (attempt {}/{}): {}", attempts, maxRetries + 1, ex.getMessage());
                    sleep(props.getRetryDelayMs() * (long) attempts);
                } else {
                    throw ex;
                }
            }
        }
    }

    private void validateApiKey() {
        String key = props.getApiKey();
        if (key == null || key.isBlank() || key.equals("YOUR_GEMINI_API_KEY_HERE")) {
            throw new GeminiException(
                "Gemini API key is not configured. Set gemini.api-key in application.properties. Get free key from https://aistudio.google.com/apikey",
                "INVALID_API_KEY", false);
        }
    }

    /**
     * Constructs request body for Gemini API
     */
    private String buildRequestBody(String prompt) {
        try {
            ObjectNode root = objectMapper.createObjectNode();
            
            // contents array
            ArrayNode contents = root.putArray("contents");
            ObjectNode content = contents.addObject();
            
            // parts array
            ArrayNode parts = content.putArray("parts");
            ObjectNode part = parts.addObject();
            part.put("text", prompt);
            
            // generation config
            ObjectNode generationConfig = root.putObject("generationConfig");
            generationConfig.put("temperature", props.getTemperature());
            generationConfig.put("maxOutputTokens", props.getMaxOutputTokens());

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            throw new GeminiException("Failed to build Gemini request body: " + e.getMessage(),
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
            post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));

            log.debug("Gemini API Request URL: {}", url);
            log.debug("Gemini API Request Body: {}", body);

            return httpClient.execute(post, response -> {
                int statusCode = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                log.debug("Gemini API status: {}", statusCode);
                log.debug("Gemini API Response Body: {}", responseBody);

                if (statusCode == 200) {
                    return responseBody;
                }
                if (statusCode == 400) {
                    throw new GeminiException(
                        "Gemini rejected the request (400). Check your prompt or API key. Response: " + responseBody,
                        "INVALID_REQUEST", false);
                }
                if (statusCode == 401) {
                    throw new GeminiException(
                        "Gemini authentication failed (401). Check your API key.",
                        "INVALID_API_KEY", false);
                }
                if (statusCode == 429) {
                    throw new GeminiException(
                        "Gemini rate limit exceeded (429). Please wait.",
                        "RATE_LIMIT", true);
                }
                if (statusCode >= 500) {
                    throw new GeminiException(
                        "Gemini server error (" + statusCode + "). Retrying...",
                        "HTTP_ERROR", true);
                }
                throw new GeminiException(
                    "Unexpected Gemini HTTP status: " + statusCode,
                    "HTTP_ERROR", false);
            });

        } catch (GeminiException ex) {
            throw ex;
        } catch (SocketTimeoutException ex) {
            throw new GeminiException(
                "Gemini API timed out. Check connection or increase gemini.read-timeout-ms.",
                "TIMEOUT", true);
        } catch (IOException ex) {
            throw new GeminiException(
                "Network error: " + ex.getMessage(),
                "NETWORK_ERROR", true);
        }
    }

    /**
     * Extracts text from Gemini response:
     * candidates[0].content.parts[0].text
     */
    private String parseResponse(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);

            if (root.has("error")) {
                JsonNode error = root.get("error");
                String message = error.path("message").asText("Unknown Gemini error");
                throw new GeminiException("Gemini API error: " + message,
                        "HTTP_ERROR", false);
            }

            JsonNode candidates = root.path("candidates");
            if (candidates.isMissingNode() || !candidates.isArray() || candidates.isEmpty()) {
                throw new GeminiException(
                    "Gemini returned no candidates in response.",
                    "EMPTY_RESPONSE", false);
            }

            String text = candidates.get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText("");

            if (text.isBlank()) {
                throw new GeminiException(
                    "Gemini returned empty response.",
                    "EMPTY_RESPONSE", false);
            }

            return text.trim();

        } catch (GeminiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GeminiException(
                "Failed to parse Gemini response: " + ex.getMessage(),
                "PARSE_ERROR", false);
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
