package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.CompanyPromptBuilder;
import com.careerpilot.dto.ai.CompanyPreparationRequestDTO;
import com.careerpilot.dto.ai.CompanyPreparationResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.service.CompanyPreparationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class CompanyPreparationServiceImpl implements CompanyPreparationService {
    private static final Logger log = LoggerFactory.getLogger(CompanyPreparationServiceImpl.class);

    private final GeminiHttpClient geminiHttpClient;
    private final CompanyPromptBuilder companyPromptBuilder;
    private final ObjectMapper objectMapper;

    public CompanyPreparationServiceImpl(GeminiHttpClient geminiHttpClient,
                                        CompanyPromptBuilder companyPromptBuilder,
                                        ObjectMapper objectMapper) {
        this.geminiHttpClient = geminiHttpClient;
        this.companyPromptBuilder = companyPromptBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public CompanyPreparationResponseDTO getCompanyPreparation(CompanyPreparationRequestDTO request, User user) {
        log.info("Starting company preparation for user: {}", user.getId());

        long startTime = System.currentTimeMillis();

        // Build prompt
        String prompt = companyPromptBuilder.buildCompanyPreparationPrompt(
                request.getCompany(),
                request.getRole()
        );

        // Call Gemini
        String response = geminiHttpClient.generate(prompt);

        // Parse response
        CompanyPreparationResponseDTO result = parseResponse(response);

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("Company preparation completed for user: {} in {} ms", user.getId(), processingTime);

        return result;
    }

    private CompanyPreparationResponseDTO parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse;
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3);
                cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
            }

            return objectMapper.readValue(cleanJson.trim(), CompanyPreparationResponseDTO.class);
        } catch (Exception e) {
            log.error("Failed to parse Gemini response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}
