package com.careerpilot.service;

import com.careerpilot.dto.ai.MockInterviewRequestDTO;
import com.careerpilot.dto.ai.MockInterviewResponseDTO;
import com.careerpilot.entity.User;

public interface MockInterviewService {
    MockInterviewResponseDTO generateQuestion(MockInterviewRequestDTO request, User user);
    MockInterviewResponseDTO evaluateAnswer(MockInterviewRequestDTO request, User user);
    MockInterviewResponseDTO getInterviewSummary(MockInterviewRequestDTO request, User user);
}
