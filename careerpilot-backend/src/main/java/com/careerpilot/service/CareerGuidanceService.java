package com.careerpilot.service;

import com.careerpilot.dto.ai.CareerGuidanceRequestDTO;
import com.careerpilot.dto.ai.CareerGuidanceResponseDTO;
import com.careerpilot.entity.User;

public interface CareerGuidanceService {
    CareerGuidanceResponseDTO getCareerGuidance(CareerGuidanceRequestDTO request, User user);
}
