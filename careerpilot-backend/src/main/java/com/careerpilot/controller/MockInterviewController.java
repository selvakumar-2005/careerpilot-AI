package com.careerpilot.controller;

import com.careerpilot.dto.ai.MockInterviewRequestDTO;
import com.careerpilot.dto.ai.MockInterviewResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.MockInterviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/mock-interview")
@CrossOrigin("${app.cors.allowed-origins}")
public class MockInterviewController {
    private static final Logger log = LoggerFactory.getLogger(MockInterviewController.class);

    private final MockInterviewService mockInterviewService;

    public MockInterviewController(MockInterviewService mockInterviewService) {
        this.mockInterviewService = mockInterviewService;
    }

    @PostMapping("/generate-question")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> generateQuestion(@RequestBody MockInterviewRequestDTO request,
                                              @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Mock interview question generation request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            MockInterviewResponseDTO response = mockInterviewService.generateQuestion(request, user);

            log.info("Mock interview question generated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating question: " + e.getMessage());
        }
    }

    @PostMapping("/evaluate-answer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> evaluateAnswer(@RequestBody MockInterviewRequestDTO request,
                                            @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Mock interview answer evaluation request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            MockInterviewResponseDTO response = mockInterviewService.evaluateAnswer(request, user);

            log.info("Mock interview answer evaluated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error evaluating answer: " + e.getMessage());
        }
    }

    @PostMapping("/summary")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getInterviewSummary(@RequestBody MockInterviewRequestDTO request,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        try {
            log.info("Interview summary request received from user: {}", principal.getId());

            User user = new User();
            user.setId(principal.getId());

            MockInterviewResponseDTO response = mockInterviewService.getInterviewSummary(request, user);

            log.info("Interview summary generated successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating summary: " + e.getMessage());
        }
    }
}
