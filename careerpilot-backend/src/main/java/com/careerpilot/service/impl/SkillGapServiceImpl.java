package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.SkillGapPromptBuilder;
import com.careerpilot.dto.ai.SkillGapRequestDTO;
import com.careerpilot.dto.ai.SkillGapResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.service.SkillGapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SkillGapServiceImpl implements SkillGapService {
    private static final Logger log = LoggerFactory.getLogger(SkillGapServiceImpl.class);

    private final GeminiHttpClient geminiHttpClient;
    private final SkillGapPromptBuilder skillGapPromptBuilder;
    private final ObjectMapper objectMapper;

    public SkillGapServiceImpl(GeminiHttpClient geminiHttpClient,
                              SkillGapPromptBuilder skillGapPromptBuilder,
                              ObjectMapper objectMapper) {
        this.geminiHttpClient = geminiHttpClient;
        this.skillGapPromptBuilder = skillGapPromptBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public SkillGapResponseDTO analyzeSkillGap(SkillGapRequestDTO request, User user) {
        log.info("Starting skill gap analysis for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        // Validate input
        if (request.getCurrentSkills() == null || request.getCurrentSkills().trim().isEmpty()) {
            throw new IllegalArgumentException("Current skills are required");
        }
        if (request.getTargetRole() == null || request.getTargetRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Target role is required");
        }

        // Build prompt
        String prompt = skillGapPromptBuilder.buildSkillGapAnalysisPrompt(
                request.getCurrentSkills(),
                request.getTargetRole(),
                request.getIndustry() != null ? request.getIndustry() : "Technology",
                request.getExperienceLevel() != null ? request.getExperienceLevel() : "Mid-Level",
                request.getYearsOfExperience() != null ? request.getYearsOfExperience() : "0"
        );

        // Call Groq API
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        SkillGapResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Skill gap analysis completed for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    private SkillGapResponseDTO parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse;
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            }

            return objectMapper.readValue(cleanJson.trim(), SkillGapResponseDTO.class);
        } catch (Exception e) {
            log.error("Failed to parse Groq response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}
