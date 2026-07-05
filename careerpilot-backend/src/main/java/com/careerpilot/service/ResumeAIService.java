package com.careerpilot.service;

import com.careerpilot.dto.ai.ResumeAIRequestDTO;
import com.careerpilot.dto.ai.ResumeAIResponseDTO;
import com.careerpilot.entity.User;

public interface ResumeAIService {
    ResumeAIResponseDTO analyzeResume(ResumeAIRequestDTO request, User user);
}
