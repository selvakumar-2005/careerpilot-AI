package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.InterviewPromptBuilder;
import com.careerpilot.dto.ai.InterviewRequestDTO;
import com.careerpilot.dto.ai.InterviewResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.service.InterviewGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class InterviewGeneratorServiceImpl implements InterviewGeneratorService {
    private static final Logger log = LoggerFactory.getLogger(InterviewGeneratorServiceImpl.class);

    private final GeminiHttpClient geminiHttpClient;
    private final InterviewPromptBuilder interviewPromptBuilder;
    private final ObjectMapper objectMapper;

    public InterviewGeneratorServiceImpl(GeminiHttpClient geminiHttpClient,
                                       InterviewPromptBuilder interviewPromptBuilder,
                                       ObjectMapper objectMapper) {
        this.geminiHttpClient = geminiHttpClient;
        this.interviewPromptBuilder = interviewPromptBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public InterviewResponseDTO generateInterviewQuestions(InterviewRequestDTO request, User user) {
        log.info("Starting interview generation for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        // Build prompt
        String prompt = interviewPromptBuilder.buildInterviewGenerationPrompt(
                request.getCompany(),
                request.getTechnology(),
                request.getDifficulty(),
                request.getExperienceLevel()
        );

        // Call Gemini
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        InterviewResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Interview generation completed for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    private InterviewResponseDTO parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse;
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            }

            return objectMapper.readValue(cleanJson.trim(), InterviewResponseDTO.class);
        } catch (Exception e) {
            log.error("Failed to parse Gemini response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}
