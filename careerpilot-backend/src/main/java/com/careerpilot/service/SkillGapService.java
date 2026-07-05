package com.careerpilot.service;

import com.careerpilot.dto.ai.SkillGapRequestDTO;
import com.careerpilot.dto.ai.SkillGapResponseDTO;
import com.careerpilot.entity.User;

public interface SkillGapService {
    SkillGapResponseDTO analyzeSkillGap(SkillGapRequestDTO request, User user);
}
