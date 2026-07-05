package com.careerpilot.service.impl;

import com.careerpilot.ai.GeminiHttpClient;
import com.careerpilot.ai.ChatPromptBuilder;
import com.careerpilot.dto.ai.ChatRequestDTO;
import com.careerpilot.dto.ai.ChatResponseDTO;
import com.careerpilot.entity.ChatMessage;
import com.careerpilot.entity.User;
import com.careerpilot.repository.ChatMessageRepository;
import com.careerpilot.service.CareerChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerChatServiceImpl implements CareerChatService {
    private static final Logger log = LoggerFactory.getLogger(CareerChatServiceImpl.class);

    private final GeminiHttpClient geminiHttpClient;
    private final ChatPromptBuilder chatPromptBuilder;
    private final ChatMessageRepository chatMessageRepository;

    public CareerChatServiceImpl(GeminiHttpClient geminiHttpClient,
                                ChatPromptBuilder chatPromptBuilder,
                                ChatMessageRepository chatMessageRepository) {
        this.geminiHttpClient = geminiHttpClient;
        this.chatPromptBuilder = chatPromptBuilder;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public ChatResponseDTO sendMessage(ChatRequestDTO request, User user) {
        log.info("Processing chat message from user: {}", user.getId());

        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        // Build prompt
        String prompt = chatPromptBuilder.buildChatPrompt(request.getMessage());

        // Call Gemini
        String response = geminiHttpClient.generate(prompt);

        // Save to database
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setUserMessage(request.getMessage());
        chatMessage.setAiResponse(response);
        ChatMessage saved = chatMessageRepository.save(chatMessage);

        log.info("Chat message saved for user: {} with ID: {}", user.getId(), saved.getId());

        // Build response DTO
        ChatResponseDTO chatResponseDTO = new ChatResponseDTO();
        chatResponseDTO.setMessageId(saved.getId());
        chatResponseDTO.setUserMessage(request.getMessage());
        chatResponseDTO.setResponse(response);
        chatResponseDTO.setTimestamp(System.currentTimeMillis());

        return chatResponseDTO;
    }

    @Override
    public List<ChatResponseDTO> getChatHistory(User user) {
        log.info("Fetching chat history for user: {}", user.getId());

        return chatMessageRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(msg -> new ChatResponseDTO(
                        msg.getId(),
                        msg.getAiResponse(),
                        msg.getUserMessage(),
                        msg.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void clearChatHistory(User user) {
        log.info("Clearing chat history for user: {}", user.getId());

        List<ChatMessage> messages = chatMessageRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        chatMessageRepository.deleteAll(messages);
    }
}
