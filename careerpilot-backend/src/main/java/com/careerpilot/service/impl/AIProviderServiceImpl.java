package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.GroqHttpClient;
import com.careerpilot.service.AIProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Smart AI Provider Service with intelligent live failover.
 * 
 * STRATEGY:
 * - Primary: Gemini (better quality, unlimited)
 * - Fallback: Groq (free tier, fast)
 * 
 * BEHAVIOR:
 * 1. Try Gemini first
 * 2. If any error occurs → Immediately try Groq
 * 3. Live health monitoring to detect provider status
 * 4. Automatic switching on any error (not just rate limits)
 * 5. Always provides response if any provider works
 */
@Service
public class AIProviderServiceImpl implements AIProviderService {
    
    private static final Logger log = LoggerFactory.getLogger(AIProviderServiceImpl.class);
    
    private final GeminiHttpClient geminiHttpClient;
    private final GroqHttpClient groqHttpClient;
    private String lastProviderUsed = "GEMINI";
    private boolean geminiHealthy = true;
    private boolean groqHealthy = true;
    
    private static final String GEMINI = "GEMINI";
    private static final String GROQ = "GROQ";
    
    public AIProviderServiceImpl(GeminiHttpClient geminiHttpClient, 
                               GroqHttpClient groqHttpClient) {
        this.geminiHttpClient = geminiHttpClient;
        this.groqHttpClient = groqHttpClient;
    }
    
    @Override
    public String generateContent(String prompt) {
        return generateContent(prompt, 120000); // Default 2 min timeout
    }
    
    @Override
    public String generateContent(String prompt, long timeoutMs) {
        log.info("═══════════════════════════════════════════════════════════════════");
        log.info("AIProviderService: Attempting to generate content");
        log.info("Provider Health - Gemini: {} | Groq: {}", 
                 geminiHealthy ? "✅ OK" : "❌ DOWN",
                 groqHealthy ? "✅ OK" : "❌ DOWN");
        log.info("═══════════════════════════════════════════════════════════════════");
        
        // Try primary provider (Gemini)
        if (geminiHealthy) {
            try {
                log.info("🔵 [PRIMARY] Attempting Gemini API...");
                String response = geminiHttpClient.generate(prompt);
                lastProviderUsed = GEMINI;
                geminiHealthy = true;
                log.info("✅ [SUCCESS] Gemini API succeeded ✓ USING GEMINI PROVIDER");
                log.info("═══════════════════════════════════════════════════════════════════");
                return response;
            } catch (Exception geminiError) {
                String errorMsg = geminiError.getMessage() != null ? geminiError.getMessage() : "";
                log.warn("❌ [PRIMARY ERROR] Gemini failed: {}", errorMsg);
                
                // Mark Gemini as unhealthy
                geminiHealthy = false;
                
                if (isAuthError(errorMsg)) {
                    log.warn("⚠️  [AUTH ISSUE] Gemini authentication failed. Marking unhealthy.");
                } else if (isRateLimitError(errorMsg)) {
                    log.warn("⚠️  [RATE LIMIT] Gemini 429 limit. Marking unhealthy.");
                } else {
                    log.warn("⚠️  [ERROR] Gemini error detected: {}. Attempting fallback...", errorMsg);
                }
            }
        } else {
            log.warn("⏭️  [SKIPPED] Gemini marked unhealthy. Proceeding to fallback.");
        }
        
        // Try fallback provider (Groq)
        if (groqHealthy) {
            try {
                log.info("🟡 [FALLBACK] Attempting Groq API...");
                String response = groqHttpClient.generate(prompt);
                lastProviderUsed = GROQ;
                groqHealthy = true;
                log.info("✅ [SUCCESS] Groq API succeeded ✓ USING GROQ PROVIDER (FALLBACK ACTIVE)");
                log.info("═══════════════════════════════════════════════════════════════════");
                return response;
            } catch (Exception groqError) {
                String errorMsg = groqError.getMessage() != null ? groqError.getMessage() : "";
                log.error("❌ [FALLBACK ERROR] Groq failed: {}", errorMsg);
                
                // Mark Groq as unhealthy
                groqHealthy = false;
                
                if (isRateLimitError(errorMsg)) {
                    log.error("⚠️  [RATE LIMIT] Groq rate limit (429) exceeded.");
                }
            }
        } else {
            log.error("⏭️  [SKIPPED] Groq marked unhealthy. Both providers unavailable.");
        }
        
        // Both providers failed
        log.error("❌❌❌ [CRITICAL] Both AI providers failed!");
        log.error("═══════════════════════════════════════════════════════════════════");
        throw new RuntimeException(
            "All AI providers currently unavailable. " +
            "Gemini and Groq both failed. " +
            "Please check your API keys and try again."
        );
    }
    
    @Override
    public boolean isGroqAvailable() {
        try {
            log.info("🔍 [HEALTH CHECK] Testing Groq provider...");
            groqHttpClient.generate("Say 'ok' only");
            groqHealthy = true;
            log.info("✅ [HEALTH CHECK] Groq is healthy");
            return true;
        } catch (Exception e) {
            groqHealthy = false;
            log.warn("❌ [HEALTH CHECK] Groq is DOWN: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean isGeminiAvailable() {
        try {
            log.info("🔍 [HEALTH CHECK] Testing Gemini provider...");
            geminiHttpClient.generate("Say 'ok' only");
            geminiHealthy = true;
            log.info("✅ [HEALTH CHECK] Gemini is healthy");
            return true;
        } catch (Exception e) {
            geminiHealthy = false;
            log.warn("❌ [HEALTH CHECK] Gemini is DOWN: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public String getLastProviderUsed() {
        return lastProviderUsed;
    }
    
    /**
     * Manually reset provider health status
     * Call this when providers come back online
     */
    public void resetProviderHealth() {
        log.info("🔄 [MANUAL RESET] Resetting provider health status...");
        geminiHealthy = true;
        groqHealthy = true;
        log.info("✅ All providers marked healthy. Ready to retry.");
    }
    
    /**
     * Get current health status
     */
    public String getProviderStatus() {
        return String.format("Gemini: %s | Groq: %s | Last Used: %s",
                geminiHealthy ? "✅ Healthy" : "❌ Down",
                groqHealthy ? "✅ Healthy" : "❌ Down",
                lastProviderUsed);
    }
    
    /**
     * Check if error is a rate limit error (429, quota exceeded, etc.)
     */
    private boolean isRateLimitError(String errorMessage) {
        if (errorMessage == null) return false;
        String lower = errorMessage.toLowerCase();
        return errorMessage.contains("429") || 
               lower.contains("rate limit") ||
               lower.contains("quota") ||
               lower.contains("limit exceeded");
    }
    
    /**
     * Check if error is authentication error (401, 403, invalid key, etc.)
     */
    private boolean isAuthError(String errorMessage) {
        if (errorMessage == null) return false;
        String lower = errorMessage.toLowerCase();
        return errorMessage.contains("401") || 
               errorMessage.contains("403") ||
               lower.contains("authentication") ||
               lower.contains("invalid api key") ||
               lower.contains("api key not valid") ||
               lower.contains("unauthorized");
    }
}
