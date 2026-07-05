package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ai.CodingAIRequestDTO;
import com.careerpilot.dto.ai.CodingAIResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.CodingAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/coding")
@CrossOrigin(origins = "http://localhost:3000")
public class CodingAIController {
    private static final Logger log = LoggerFactory.getLogger(CodingAIController.class);

    private final CodingAIService codingAIService;

    public CodingAIController(CodingAIService codingAIService) {
        this.codingAIService = codingAIService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<CodingAIResponseDTO>> analyzeCode(
            @RequestBody CodingAIRequestDTO request,
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Code analysis request from user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        CodingAIResponseDTO response = codingAIService.analyzeCode(request, user);

        return ResponseEntity.ok(ApiResponse.success("Code analysis completed successfully", response));
    }
}
