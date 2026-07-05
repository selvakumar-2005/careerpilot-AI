package com.careerpilot.controller;

import com.careerpilot.dto.ai.SkillGapRequestDTO;
import com.careerpilot.dto.ai.SkillGapResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.SkillGapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/skill-gap")
@CrossOrigin("${app.cors.allowed-origins}")
public class SkillGapController {
    private static final Logger log = LoggerFactory.getLogger(SkillGapController.class);

    private final SkillGapService skillGapService;

    public SkillGapController(SkillGapService skillGapService) {
        this.skillGapService = skillGapService;
    }

    @PostMapping("/analyze")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> analyzeSkillGap(@RequestBody SkillGapRequestDTO request,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Skill gap analysis request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            SkillGapResponseDTO response = skillGapService.analyzeSkillGap(request, user);

            log.info("Skill gap analysis completed successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error analyzing skill gap: " + e.getMessage());
        }
    }
}
