package com.careerpilot.service;

import com.careerpilot.dto.ai.CompanyPreparationRequestDTO;
import com.careerpilot.dto.ai.CompanyPreparationResponseDTO;
import com.careerpilot.entity.User;

public interface CompanyPreparationService {
    CompanyPreparationResponseDTO getCompanyPreparation(CompanyPreparationRequestDTO request, User user);
}
