package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ai.ResumeAIRequestDTO;
import com.careerpilot.dto.ai.ResumeAIResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.ResumeAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/resume")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeAIController {
    private static final Logger log = LoggerFactory.getLogger(ResumeAIController.class);

    private final ResumeAIService resumeAIService;

    public ResumeAIController(ResumeAIService resumeAIService) {
        this.resumeAIService = resumeAIService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<ResumeAIResponseDTO>> analyzeResume(
            @RequestBody ResumeAIRequestDTO request,
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Resume analysis request from user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        ResumeAIResponseDTO response = resumeAIService.analyzeResume(request, user);

        return ResponseEntity.ok(ApiResponse.success("Resume analysis completed successfully", response));
    }
}
