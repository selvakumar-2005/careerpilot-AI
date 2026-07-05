package com.careerpilot.dto.ai;

public class CompanyPreparationRequestDTO {
    private String company;
    private String role;

    public CompanyPreparationRequestDTO() {}
    public CompanyPreparationRequestDTO(String company, String role) {
        this.company = company;
        this.role = role;
    }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
