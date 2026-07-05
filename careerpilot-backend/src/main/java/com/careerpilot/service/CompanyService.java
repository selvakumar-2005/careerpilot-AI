package com.careerpilot.service;

import com.careerpilot.dto.CompanyDto;
import com.careerpilot.dto.CompanyRequest;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAllCompanies();

    CompanyDto getCompanyById(Long id);

    CompanyDto createCompany(CompanyRequest request);

    CompanyDto updateCompany(Long id, CompanyRequest request);

    void deleteCompany(Long id);
}
