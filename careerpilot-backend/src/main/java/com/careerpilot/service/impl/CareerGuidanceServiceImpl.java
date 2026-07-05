package com.careerpilot.service.impl;

import com.careerpilot.ai.CareerPromptBuilder;
import com.careerpilot.dto.ai.CareerGuidanceRequestDTO;
import com.careerpilot.dto.ai.CareerGuidanceResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.service.AIProviderService;
import com.careerpilot.service.CareerGuidanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CareerGuidanceServiceImpl implements CareerGuidanceService {
    private static final Logger log = LoggerFactory.getLogger(CareerGuidanceServiceImpl.class);

    private final AIProviderService aiProviderService;
    private final CareerPromptBuilder careerPromptBuilder;
    private final ObjectMapper objectMapper;

    public CareerGuidanceServiceImpl(AIProviderService aiProviderService,
                                    CareerPromptBuilder careerPromptBuilder,
                                    ObjectMapper objectMapper) {
        this.aiProviderService = aiProviderService;
        this.careerPromptBuilder = careerPromptBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public CareerGuidanceResponseDTO getCareerGuidance(CareerGuidanceRequestDTO request, User user) {
        log.info("Starting career guidance for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        String prompt = careerPromptBuilder.buildCareerGuidancePrompt(
                request.getCurrentSkills(),
                request.getCurrentSemester(),
                request.getTargetCompany(),
                request.getTargetRole(),
                request.getCurrentCGPA()
        );

        // Use smart failover system - tries Gemini, falls back to Groq if needed
        String response = aiProviderService.generateContent(prompt);
        String provider = aiProviderService.getLastProviderUsed();

        CareerGuidanceResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Career guidance completed for user: {} in {} ms [Provider: {}]", 
                user.getId(), processingTime, provider);

        return result;
    }

    public CareerGuidanceResponseDTO getPersonalizedRecommendations(String currentSkills, String targetRole, 
                                                                    String experience, String interests, User user) {
        log.info("Generating personalized recommendations for user: {}", user.getId());

        if (targetRole == null || targetRole.trim().isEmpty()) {
            throw new IllegalArgumentException("Target role is required");
        }

        long startTime = System.currentTimeMillis();

        String prompt = careerPromptBuilder.buildPersonalizedRecommendationPrompt(
                currentSkills != null ? currentSkills : "",
                targetRole,
                experience != null ? experience : "Entry Level",
                interests != null ? interests : ""
        );

        String response = aiProviderService.generateContent(prompt);
        String provider = aiProviderService.getLastProviderUsed();

        CareerGuidanceResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Personalized recommendations generated for user: {} in {} ms [Provider: {}]", 
                user.getId(), processingTime, provider);

        return result;
    }

    public CareerGuidanceResponseDTO getIndustrySpecificGuidance(String targetRole, String industry, String level, User user) {
        log.info("Generating industry-specific guidance for user: {}", user.getId());

        if (industry == null || industry.trim().isEmpty()) {
            throw new IllegalArgumentException("Industry is required");
        }

        long startTime = System.currentTimeMillis();

        String prompt = careerPromptBuilder.buildIndustrySpecificGuidancePrompt(
                targetRole != null ? targetRole : "General Role",
                industry,
                level != null ? level : "Mid-Level"
        );

        String response = aiProviderService.generateContent(prompt);
        String provider = aiProviderService.getLastProviderUsed();

        CareerGuidanceResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Industry-specific guidance generated for user: {} in {} ms [Provider: {}]", 
                user.getId(), processingTime, provider);

        return result;
    }

    public CareerGuidanceResponseDTO getLearningRoadmap(String targetRole, String currentLevel, String timeframe, User user) {
        log.info("Generating learning roadmap for user: {}", user.getId());

        if (targetRole == null || targetRole.trim().isEmpty()) {
            throw new IllegalArgumentException("Target role is required");
        }

        long startTime = System.currentTimeMillis();

        String prompt = careerPromptBuilder.buildLearningRoadmapPrompt(
                targetRole,
                currentLevel != null ? currentLevel : "Beginner",
                timeframe != null ? timeframe : "6 months"
        );

        String response = aiProviderService.generateContent(prompt);
        String provider = aiProviderService.getLastProviderUsed();

        CareerGuidanceResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Learning roadmap generated for user: {} in {} ms [Provider: {}]", 
                user.getId(), processingTime, provider);

        return result;
    }

    public CareerGuidanceResponseDTO getCertificationRecommendations(String targetRole, String budget, String timeframe, User user) {
        log.info("Generating certification recommendations for user: {}", user.getId());

        if (targetRole == null || targetRole.trim().isEmpty()) {
            throw new IllegalArgumentException("Target role is required");
        }

        long startTime = System.currentTimeMillis();

        String prompt = careerPromptBuilder.buildCertificationRecommendationPrompt(
                targetRole,
                budget != null ? budget : "No specific budget",
                timeframe != null ? timeframe : "3 months"
        );

        String response = aiProviderService.generateContent(prompt);
        String provider = aiProviderService.getLastProviderUsed();

        CareerGuidanceResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Certification recommendations generated for user: {} in {} ms [Provider: {}]", 
                user.getId(), processingTime, provider);

        return result;
    }

    private CareerGuidanceResponseDTO parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse;
            
            // Remove markdown code blocks
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            }
            
            // Fix multiline string concatenation
            cleanJson = cleanJson.replaceAll("\"\\s+\"", " ");
            cleanJson = cleanJson.replaceAll("\"\\s*\\+\\s*\"", " ");
            cleanJson = cleanJson.replaceAll("\\s+\\+\\s+", " ");

            return objectMapper.readValue(cleanJson.trim(), CareerGuidanceResponseDTO.class);
        } catch (Exception e) {
            log.error("Failed to parse AI response: {} | Response snippet: {}", 
                    e.getMessage(), 
                    jsonResponse.length() > 300 ? jsonResponse.substring(0, 300) + "..." : jsonResponse);
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}
