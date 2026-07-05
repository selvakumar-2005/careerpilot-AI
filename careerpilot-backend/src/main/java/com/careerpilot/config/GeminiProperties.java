package com.careerpilot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Strongly-typed configuration properties for the Gemini AI integration.
 * Used as fallback provider when Groq hits rate limit.
 */
@Primary
@Configuration
@ConfigurationProperties(prefix = "gemini")
public class GeminiProperties {

    private String apiKey;
    private String model = "gemini-2.0-flash";
    private String baseUrl = "https://generativelanguage.googleapis.com";
    private String apiVersion = "v1beta";
    private int connectTimeoutMs = 10000;
    private int readTimeoutMs = 60000;
    private int maxOutputTokens = 8192;
    private double temperature = 0.7;
    private int maxRetries = 2;
    private int retryDelayMs = 1000;

    // Getters & Setters
    public String getApiKey() { return apiKey; }
    public void setApiKey(String v) { this.apiKey = v; }

    public String getModel() { return model; }
    public void setModel(String v) { this.model = v; }

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String v) { this.baseUrl = v; }

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String v) { this.apiVersion = v; }

    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public void setConnectTimeoutMs(int v) { this.connectTimeoutMs = v; }

    public int getReadTimeoutMs() { return readTimeoutMs; }
    public void setReadTimeoutMs(int v) { this.readTimeoutMs = v; }

    public int getMaxOutputTokens() { return maxOutputTokens; }
    public void setMaxOutputTokens(int v) { this.maxOutputTokens = v; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double v) { this.temperature = v; }

    public int getMaxRetries() { return maxRetries; }
    public void setMaxRetries(int v) { this.maxRetries = v; }

    public int getRetryDelayMs() { return retryDelayMs; }
    public void setRetryDelayMs(int v) { this.retryDelayMs = v; }

    /**
     * Builds the full generateContent endpoint URL for the configured model.
     */
    public String buildEndpointUrl() {
        return baseUrl + "/" + apiVersion + "/models/" + model + ":generateContent";
    }
}

