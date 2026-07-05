package com.careerpilot.service;

import com.careerpilot.dto.ai.AIRequest;
import com.careerpilot.dto.ai.AIResponse;
import com.careerpilot.dto.ai.PromptRequest;
import com.careerpilot.dto.ai.PromptResponse;

/**
 * Central AI service contract.
 * Every AI endpoint in the application calls this service.
 * The implementation is responsible for:
 *   - Delegating to GeminiHttpClient
 *   - Logging every successful request to AIRequestLog
 *   - Measuring and returning processing time
 */
public interface AIService {

    /**
     * Sends a raw prompt to Gemini and returns the response.
     *
     * @param request  Contains the prompt text (and optional context hint)
     * @param userId   The authenticated user's ID — used for request logging
     * @return         AIResponse with the model's text output
     */
    AIResponse sendPrompt(AIRequest request, Long userId);

    /**
     * Resolves a named template, injects the user content, sends to Gemini.
     *
     * @param request  Contains templateKey, userContent, optional extraParams
     * @param userId   The authenticated user's ID — used for request logging
     * @return         PromptResponse wrapping the AIResponse plus the template key used
     */
    PromptResponse sendTemplatePrompt(PromptRequest request, Long userId);
}
