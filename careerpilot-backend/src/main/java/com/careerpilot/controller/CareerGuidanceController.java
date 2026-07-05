package com.careerpilot.controller;

import com.careerpilot.dto.ai.CareerGuidanceRequestDTO;
import com.careerpilot.dto.ai.CareerGuidanceResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.CareerGuidanceService;
import com.careerpilot.service.impl.CareerGuidanceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/career")
@CrossOrigin(origins = "http://localhost:3000")
public class CareerGuidanceController {
    private static final Logger log = LoggerFactory.getLogger(CareerGuidanceController.class);

    private final CareerGuidanceService careerGuidanceService;

    public CareerGuidanceController(CareerGuidanceService careerGuidanceService) {
        this.careerGuidanceService = careerGuidanceService;
    }

    @PostMapping("/guidance")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCareerGuidance(
            @RequestBody CareerGuidanceRequestDTO request,
            @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Career guidance request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            CareerGuidanceResponseDTO response = careerGuidanceService.getCareerGuidance(request, user);

            log.info("Career guidance generated successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating guidance: " + e.getMessage());
        }
    }

    @PostMapping("/personalized-recommendations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPersonalizedRecommendations(
            @RequestParam String currentSkills,
            @RequestParam String targetRole,
            @RequestParam(required = false) String experience,
            @RequestParam(required = false) String interests,
            @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Personalized recommendations request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            CareerGuidanceServiceImpl service = (CareerGuidanceServiceImpl) careerGuidanceService;
            CareerGuidanceResponseDTO response = service.getPersonalizedRecommendations(currentSkills, targetRole, experience, interests, user);

            log.info("Personalized recommendations generated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating recommendations: " + e.getMessage());
        }
    }

    @PostMapping("/industry-guidance")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getIndustrySpecificGuidance(
            @RequestParam String industry,
            @RequestParam(required = false) String targetRole,
            @RequestParam(required = false) String level,
            @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Industry guidance request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            CareerGuidanceServiceImpl service = (CareerGuidanceServiceImpl) careerGuidanceService;
            CareerGuidanceResponseDTO response = service.getIndustrySpecificGuidance(targetRole, industry, level, user);

            log.info("Industry guidance generated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating guidance: " + e.getMessage());
        }
    }

    @PostMapping("/learning-roadmap")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getLearningRoadmap(
            @RequestParam String targetRole,
            @RequestParam(required = false) String currentLevel,
            @RequestParam(required = false) String timeframe,
            @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Learning roadmap request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            CareerGuidanceServiceImpl service = (CareerGuidanceServiceImpl) careerGuidanceService;
            CareerGuidanceResponseDTO response = service.getLearningRoadmap(targetRole, currentLevel, timeframe, user);

            log.info("Learning roadmap generated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating roadmap: " + e.getMessage());
        }
    }

    @PostMapping("/certifications")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCertificationRecommendations(
            @RequestParam String targetRole,
            @RequestParam(required = false) String budget,
            @RequestParam(required = false) String timeframe,
            @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Certification recommendations request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            CareerGuidanceServiceImpl service = (CareerGuidanceServiceImpl) careerGuidanceService;
            CareerGuidanceResponseDTO response = service.getCertificationRecommendations(targetRole, budget, timeframe, user);

            log.info("Certification recommendations generated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating recommendations: " + e.getMessage());
        }
    }
}
