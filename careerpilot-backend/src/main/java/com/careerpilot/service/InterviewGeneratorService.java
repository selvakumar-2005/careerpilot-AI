package com.careerpilot.service;

import com.careerpilot.dto.ai.InterviewRequestDTO;
import com.careerpilot.dto.ai.InterviewResponseDTO;
import com.careerpilot.entity.User;

public interface InterviewGeneratorService {
    InterviewResponseDTO generateInterviewQuestions(InterviewRequestDTO request, User user);
}
