package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ai.CompanyPreparationRequestDTO;
import com.careerpilot.dto.ai.CompanyPreparationResponseDTO;
import com.careerpilot.entity.User;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.CompanyPreparationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyPreparationController {
    private static final Logger log = LoggerFactory.getLogger(CompanyPreparationController.class);

    private final CompanyPreparationService companyPreparationService;

    public CompanyPreparationController(CompanyPreparationService companyPreparationService) {
        this.companyPreparationService = companyPreparationService;
    }

    @PostMapping("/preparation")
    public ResponseEntity<ApiResponse<CompanyPreparationResponseDTO>> getCompanyPreparation(
            @RequestBody CompanyPreparationRequestDTO request,
            @AuthenticationPrincipal UserPrincipal principal) {

        log.info("Company preparation request from user: {}", principal.getId());

        User user = new User();
        user.setId(principal.getId());

        CompanyPreparationResponseDTO response = companyPreparationService.getCompanyPreparation(request, user);

        return ResponseEntity.ok(ApiResponse.success("Company preparation guide generated successfully", response));
    }
}
