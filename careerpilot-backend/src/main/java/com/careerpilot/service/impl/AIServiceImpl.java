package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.PromptTemplateService;
import com.careerpilot.dto.ai.AIRequest;
import com.careerpilot.dto.ai.AIResponse;
import com.careerpilot.dto.ai.PromptRequest;
import com.careerpilot.dto.ai.PromptResponse;
import com.careerpilot.entity.AIRequestLog;
import com.careerpilot.exception.BadRequestException;
import com.careerpilot.exception.ai.GeminiException;
import com.careerpilot.repository.AIRequestLogRepository;
import com.careerpilot.repository.UserRepository;
import com.careerpilot.service.AIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Core AI service implementation.
 *
 * Orchestrates:
 *   1. Input validation
 *   2. Prompt building (raw or template-based)
 *   3. Gemini API call via GeminiHttpClient
 *   4. Persisting the AIRequestLog on success
 *   5. Wrapping the result in the appropriate response DTO
 *
 * Does NOT contain any business-domain logic (resume analysis,
 * interview generation, etc.) — those belong in Part 3.2 services.
 */
@Service
public class AIServiceImpl implements AIService {

    private static final Logger log = LoggerFactory.getLogger(AIServiceImpl.class);

    private final GeminiHttpClient       geminiClient;
    private final PromptTemplateService  templateService;
    private final AIRequestLogRepository logRepository;
    private final UserRepository         userRepository;

    public AIServiceImpl(GeminiHttpClient geminiClient,
                         PromptTemplateService templateService,
                         AIRequestLogRepository logRepository,
                         UserRepository userRepository) {
        this.geminiClient    = geminiClient;
        this.templateService = templateService;
        this.logRepository   = logRepository;
        this.userRepository  = userRepository;
    }

    @Override
    @Transactional
    public AIResponse sendPrompt(AIRequest request, Long userId) {
        if (request == null || request.getPrompt() == null || request.getPrompt().isBlank()) {
            throw new BadRequestException("Prompt cannot be empty");
        }

        log.info("AI prompt request from userId={} length={}", userId, request.getPrompt().length());

        long   startTime = System.currentTimeMillis();
        String responseText;

        try {
            responseText = geminiClient.generate(request.getPrompt());
        } catch (GeminiException ex) {
            log.error("Gemini call failed [{}]: {}", ex.getErrorCode(), ex.getMessage());
            throw ex; // propagated to GlobalExceptionHandler
        }

        long processingMs = System.currentTimeMillis() - startTime;

        // Persist successful request log
        persistLog(userId, request.getPrompt(), responseText);

        log.info("AI response received userId={} processingMs={}", userId, processingMs);
        return new AIResponse(responseText, "gemini", processingMs);
    }

    @Override
    @Transactional
    public PromptResponse sendTemplatePrompt(PromptRequest request, Long userId) {
        if (request == null) {
            throw new BadRequestException("PromptRequest cannot be null");
        }

        // Build the full prompt from the template
        String fullPrompt = templateService.buildPrompt(
                request.getTemplateKey(),
                request.getUserContent(),
                request.getExtraParams()
        );

        log.info("Template prompt request userId={} template={}", userId, request.getTemplateKey());

        long   startTime = System.currentTimeMillis();
        String responseText;

        try {
            responseText = geminiClient.generate(fullPrompt);
        } catch (GeminiException ex) {
            log.error("Gemini template call failed [{}]: {}", ex.getErrorCode(), ex.getMessage());
            throw ex;
        }

        long processingMs = System.currentTimeMillis() - startTime;

        // Persist with the template key as context prefix
        persistLog(userId, "[" + request.getTemplateKey() + "] " + request.getUserContent(), responseText);

        AIResponse aiResponse = new AIResponse(responseText, "gemini", processingMs);
        return new PromptResponse(request.getTemplateKey(), aiResponse);
    }

    // ── Private helpers ───────────────────────────────────────────────────

    private void persistLog(Long userId, String prompt, String response) {
        try {
            AIRequestLog entry = new AIRequestLog();
            // Truncate if necessary to fit DB column limits
            entry.setPrompt(truncate(prompt, 5000));
            entry.setResponse(truncate(response, 10000));

            if (userId != null) {
                userRepository.findById(userId).ifPresent(entry::setUser);
            }

            logRepository.save(entry);
        } catch (Exception ex) {
            // Logging failures must never break the main flow
            log.warn("Failed to persist AI request log: {}", ex.getMessage());
        }
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return null;
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}
