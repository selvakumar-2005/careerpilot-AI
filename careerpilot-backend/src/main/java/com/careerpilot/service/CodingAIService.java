package com.careerpilot.service;

import com.careerpilot.dto.ai.CodingAIRequestDTO;
import com.careerpilot.dto.ai.CodingAIResponseDTO;
import com.careerpilot.entity.User;

public interface CodingAIService {
    CodingAIResponseDTO analyzeCode(CodingAIRequestDTO request, User user);
}
