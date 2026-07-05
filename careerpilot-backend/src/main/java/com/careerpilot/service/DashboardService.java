package com.careerpilot.service;

import com.careerpilot.dto.DashboardStatsDto;

public interface DashboardService {
    DashboardStatsDto getStats(Long userId);
}
