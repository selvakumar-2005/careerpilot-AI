package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.MockInterviewPromptBuilder;
import com.careerpilot.dto.ai.MockInterviewRequestDTO;
import com.careerpilot.dto.ai.MockInterviewResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.service.MockInterviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MockInterviewServiceImpl implements MockInterviewService {
    private static final Logger log = LoggerFactory.getLogger(MockInterviewServiceImpl.class);

    private final GeminiHttpClient geminiHttpClient;
    private final MockInterviewPromptBuilder mockInterviewPromptBuilder;
    private final ObjectMapper objectMapper;

    public MockInterviewServiceImpl(GeminiHttpClient geminiHttpClient,
                                   MockInterviewPromptBuilder mockInterviewPromptBuilder,
                                   ObjectMapper objectMapper) {
        this.geminiHttpClient = geminiHttpClient;
        this.mockInterviewPromptBuilder = mockInterviewPromptBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public MockInterviewResponseDTO generateQuestion(MockInterviewRequestDTO request, User user) {
        log.info("Generating mock interview question for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        // Validate input
        if (request.getJobRole() == null || request.getJobRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Job role is required");
        }
        if (request.getExperienceLevel() == null || request.getExperienceLevel().trim().isEmpty()) {
            throw new IllegalArgumentException("Experience level is required");
        }

        // Default values
        Integer questionNumber = request.getQuestionNumber() != null ? request.getQuestionNumber() : 1;
        Integer totalQuestions = request.getTotalQuestions() != null ? request.getTotalQuestions() : 10;

        // Build prompt
        String prompt = mockInterviewPromptBuilder.buildMockInterviewQuestionPrompt(
                request.getJobRole(),
                request.getExperienceLevel(),
                request.getCompany() != null ? request.getCompany() : "Tech Company",
                request.getTechnology() != null ? request.getTechnology() : "General Tech",
                questionNumber,
                totalQuestions
        );

        // Call Groq API
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        MockInterviewResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Mock interview question generated for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    @Override
    public MockInterviewResponseDTO evaluateAnswer(MockInterviewRequestDTO request, User user) {
        log.info("Evaluating mock interview answer for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        // Validate input
        if (request.getAnswer() == null || request.getAnswer().trim().isEmpty()) {
            throw new IllegalArgumentException("Answer is required for evaluation");
        }
        if (request.getJobRole() == null || request.getJobRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Job role is required");
        }

        // Build prompt
        String prompt = mockInterviewPromptBuilder.buildAnswerEvaluationPrompt(
                request.getJobRole(),
                request.getExperienceLevel() != null ? request.getExperienceLevel() : "Mid-Level",
                "Interview Question",
                request.getAnswer(),
                request.getTechnology() != null ? request.getTechnology() : "General Tech"
        );

        // Call Groq API
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        MockInterviewResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Mock interview answer evaluated for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    @Override
    public MockInterviewResponseDTO getInterviewSummary(MockInterviewRequestDTO request, User user) {
        log.info("Generating interview summary for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        // Build prompt
        String prompt = mockInterviewPromptBuilder.buildInterviewSummaryPrompt(
                request.getJobRole(),
                request.getExperienceLevel() != null ? request.getExperienceLevel() : "Mid-Level",
                "",
                0.0
        );

        // Call Groq API
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        MockInterviewResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Interview summary generated for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    private MockInterviewResponseDTO parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse;
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            }

            return objectMapper.readValue(cleanJson.trim(), MockInterviewResponseDTO.class);
        } catch (Exception e) {
            log.error("Failed to parse Groq response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}
