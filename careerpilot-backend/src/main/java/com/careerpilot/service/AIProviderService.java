package com.careerpilot.service;

/**
 * Service for managing AI provider fallback logic.
 * When primary provider (Groq) hits rate limit, automatically falls back to Gemini.
 */
public interface AIProviderService {
    
    /**
     * Generate content using best available provider with automatic fallback
     * @param prompt The prompt to send to AI
     * @return Generated response text
     * @throws RuntimeException if all providers fail
     */
    String generateContent(String prompt);
    
    /**
     * Generate content with timeout handling
     * @param prompt The prompt to send to AI
     * @param timeoutMs Timeout in milliseconds
     * @return Generated response text
     */
    String generateContent(String prompt, long timeoutMs);
    
    /**
     * Check if primary provider (Groq) is available
     * @return true if Groq is available, false otherwise
     */
    boolean isGroqAvailable();
    
    /**
     * Check if fallback provider (Gemini) is available
     * @return true if Gemini is available, false otherwise
     */
    boolean isGeminiAvailable();
    
    /**
     * Get the name of the last provider used
     * @return Provider name (GROQ or GEMINI)
     */
    String getLastProviderUsed();
}
