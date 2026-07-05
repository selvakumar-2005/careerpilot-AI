package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.CodingPromptBuilder;
import com.careerpilot.dto.ai.CodingAIRequestDTO;
import com.careerpilot.dto.ai.CodingAIResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.service.CodingAIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class CodingAIServiceImpl implements CodingAIService {
    private static final Logger log = LoggerFactory.getLogger(CodingAIServiceImpl.class);

    private final GeminiHttpClient geminiHttpClient;
    private final CodingPromptBuilder codingPromptBuilder;
    private final ObjectMapper objectMapper;

    public CodingAIServiceImpl(GeminiHttpClient geminiHttpClient,
                              CodingPromptBuilder codingPromptBuilder,
                              ObjectMapper objectMapper) {
        this.geminiHttpClient = geminiHttpClient;
        this.codingPromptBuilder = codingPromptBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public CodingAIResponseDTO analyzeCode(CodingAIRequestDTO request, User user) {
        log.info("Starting code analysis for user: {}", user.getId());

        // Validate input
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }

        long startTime = System.currentTimeMillis();

        // Build prompt
        String prompt = codingPromptBuilder.buildCodingAnalysisPrompt(
                request.getCode(),
                request.getLanguage(),
                request.getProblemStatement()
        );

        // Call Gemini
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        CodingAIResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Code analysis completed for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    private CodingAIResponseDTO parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse;
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            }

            return objectMapper.readValue(cleanJson.trim(), CodingAIResponseDTO.class);
        } catch (Exception e) {
            log.error("Failed to parse Gemini response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}
