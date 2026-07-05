package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ai.AIRequest;
import com.careerpilot.dto.ai.AIResponse;
import com.careerpilot.dto.ai.PromptRequest;
import com.careerpilot.dto.ai.PromptResponse;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.AIService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for AI endpoints.
 *
 * All endpoints are protected by JWT (handled by SecurityFilterChain —
 * the existing .anyRequest().authenticated() rule already covers /api/ai/**).
 *
 * Part 3.1 exposes only:
 *   POST /api/ai/test    — free-text prompt test
 *   POST /api/ai/prompt  — template-based prompt
 *
 * Part 3.2 will add feature-specific endpoints (resume, interview, etc.)
 * as separate controllers that delegate to domain services.
 */
@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    /**
     * Free-form prompt test endpoint.
     * Used by the React AI Test page to verify the Gemini integration end-to-end.
     *
     * POST /api/ai/test
     * Body: { "prompt": "Tell me about Java" }
     */
    @PostMapping("/test")
    public ResponseEntity<ApiResponse<AIResponse>> test(
            @Valid @RequestBody AIRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {

        AIResponse response = aiService.sendPrompt(request, principal.getId());
        return ResponseEntity.ok(ApiResponse.success("AI response generated", response));
    }

    /**
     * Template-based prompt endpoint.
     * Caller selects a named template and supplies the content to inject.
     *
     * POST /api/ai/prompt
     * Body: { "templateKey": "RESUME_ANALYSIS", "userContent": "..." }
     */
    @PostMapping("/prompt")
    public ResponseEntity<ApiResponse<PromptResponse>> prompt(
            @Valid @RequestBody PromptRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {

        PromptResponse response = aiService.sendTemplatePrompt(request, principal.getId());
        return ResponseEntity.ok(ApiResponse.success("Template prompt processed", response));
    }
}
