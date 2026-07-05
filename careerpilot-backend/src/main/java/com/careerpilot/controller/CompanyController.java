package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.CompanyDto;
import com.careerpilot.dto.CompanyRequest;
import com.careerpilot.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyDto>>> getAll() {
        List<CompanyDto> list = companyService.getAllCompanies();
        return ResponseEntity.ok(ApiResponse.success("Companies retrieved", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> getById(@PathVariable Long id) {
        CompanyDto dto = companyService.getCompanyById(id);
        return ResponseEntity.ok(ApiResponse.success("Company retrieved", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyDto>> create(
            @Valid @RequestBody CompanyRequest request) {
        CompanyDto dto = companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Company created", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequest request) {
        CompanyDto dto = companyService.updateCompany(id, request);
        return ResponseEntity.ok(ApiResponse.success("Company updated", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(ApiResponse.success("Company deleted"));
    }
}
