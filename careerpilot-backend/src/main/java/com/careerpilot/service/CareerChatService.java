package com.careerpilot.service;

import com.careerpilot.dto.ai.ChatRequestDTO;
import com.careerpilot.dto.ai.ChatResponseDTO;
import com.careerpilot.entity.User;
import java.util.List;

public interface CareerChatService {
    ChatResponseDTO sendMessage(ChatRequestDTO request, User user);
    List<ChatResponseDTO> getChatHistory(User user);
    void clearChatHistory(User user);
}
