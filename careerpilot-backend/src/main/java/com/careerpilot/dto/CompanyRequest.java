package com.careerpilot.dto;

import jakarta.validation.constraints.NotBlank;

public class CompanyRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String description;
    private String requiredSkills;
    private String codingTopics;
    private String technicalTopics;
    private String aptitudeTopics;
    private String interviewRounds;
    private String placementPackage;

    public CompanyRequest() {}

    public String getCompanyName()              { return companyName; }
    public void setCompanyName(String v)        { this.companyName = v; }
    public String getDescription()              { return description; }
    public void setDescription(String v)        { this.description = v; }
    public String getRequiredSkills()           { return requiredSkills; }
    public void setRequiredSkills(String v)     { this.requiredSkills = v; }
    public String getCodingTopics()             { return codingTopics; }
    public void setCodingTopics(String v)       { this.codingTopics = v; }
    public String getTechnicalTopics()          { return technicalTopics; }
    public void setTechnicalTopics(String v)    { this.technicalTopics = v; }
    public String getAptitudeTopics()           { return aptitudeTopics; }
    public void setAptitudeTopics(String v)     { this.aptitudeTopics = v; }
    public String getInterviewRounds()          { return interviewRounds; }
    public void setInterviewRounds(String v)    { this.interviewRounds = v; }
    public String getPlacementPackage()         { return placementPackage; }
    public void setPlacementPackage(String v)   { this.placementPackage = v; }
}
