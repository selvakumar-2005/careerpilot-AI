package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ai.InterviewRequestDTO;
import com.careerpilot.dto.ai.InterviewResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.InterviewGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/interview")
@CrossOrigin(origins = "http://localhost:3000")
public class InterviewGeneratorController {
    private static final Logger log = LoggerFactory.getLogger(InterviewGeneratorController.class);

    private final InterviewGeneratorService interviewGeneratorService;

    public InterviewGeneratorController(InterviewGeneratorService interviewGeneratorService) {
        this.interviewGeneratorService = interviewGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<InterviewResponseDTO>> generateInterviewQuestions(
            @RequestBody InterviewRequestDTO request,
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Interview generation request from user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        InterviewResponseDTO response = interviewGeneratorService.generateInterviewQuestions(request, user);

        return ResponseEntity.ok(ApiResponse.success("Interview questions generated successfully", response));
    }
}
