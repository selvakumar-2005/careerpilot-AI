package com.careerpilot.service.impl;

import com.careerpilot.dto.CompanyDto;
import com.careerpilot.dto.CompanyRequest;
import com.careerpilot.entity.Company;
import com.careerpilot.exception.BadRequestException;
import com.careerpilot.exception.ResourceNotFoundException;
import com.careerpilot.repository.CompanyRepository;
import com.careerpilot.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAllByOrderByCompanyNameAsc()
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return mapToDto(company);
    }

    @Override
    @Transactional
    public CompanyDto createCompany(CompanyRequest request) {
        if (companyRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())) {
            throw new BadRequestException("Company '" + request.getCompanyName() + "' already exists");
        }
        Company company = new Company();
        applyRequest(company, request);
        return mapToDto(companyRepository.save(company));
    }

    @Override
    @Transactional
    public CompanyDto updateCompany(Long id, CompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        applyRequest(company, request);
        return mapToDto(companyRepository.save(company));
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company", "id", id);
        }
        companyRepository.deleteById(id);
    }

    private void applyRequest(Company company, CompanyRequest r) {
        company.setCompanyName(r.getCompanyName());
        company.setDescription(r.getDescription());
        company.setRequiredSkills(r.getRequiredSkills());
        company.setCodingTopics(r.getCodingTopics());
        company.setTechnicalTopics(r.getTechnicalTopics());
        company.setAptitudeTopics(r.getAptitudeTopics());
        company.setInterviewRounds(r.getInterviewRounds());
        company.setPlacementPackage(r.getPlacementPackage());
    }

    private CompanyDto mapToDto(Company c) {
        CompanyDto dto = new CompanyDto();
        dto.setId(c.getId());
        dto.setCompanyName(c.getCompanyName());
        dto.setDescription(c.getDescription());
        dto.setRequiredSkills(c.getRequiredSkills());
        dto.setCodingTopics(c.getCodingTopics());
        dto.setTechnicalTopics(c.getTechnicalTopics());
        dto.setAptitudeTopics(c.getAptitudeTopics());
        dto.setInterviewRounds(c.getInterviewRounds());
        dto.setPlacementPackage(c.getPlacementPackage());
        return dto;
    }
}
