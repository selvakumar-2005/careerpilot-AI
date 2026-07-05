package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ai.ChatRequestDTO;
import com.careerpilot.dto.ai.ChatResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.CareerChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ai/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class CareerChatController {
    private static final Logger log = LoggerFactory.getLogger(CareerChatController.class);

    private final CareerChatService careerChatService;

    public CareerChatController(CareerChatService careerChatService) {
        this.careerChatService = careerChatService;
    }

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<ChatResponseDTO>> sendMessage(
            @RequestBody ChatRequestDTO request,
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Chat message from user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        ChatResponseDTO response = careerChatService.sendMessage(request, user);

        return ResponseEntity.ok(ApiResponse.success("Chat message processed", response));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<ChatResponseDTO>>> getChatHistory(
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Fetching chat history for user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        List<ChatResponseDTO> history = careerChatService.getChatHistory(user);

        return ResponseEntity.ok(ApiResponse.<List<ChatResponseDTO>>builder()
                .success(true)
                .message("Chat history retrieved")
                .data(history)
                .build());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<String>> clearChatHistory(
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Clearing chat history for user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        careerChatService.clearChatHistory(user);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Chat history cleared successfully")
                .data(null)
                .build());
    }
}
