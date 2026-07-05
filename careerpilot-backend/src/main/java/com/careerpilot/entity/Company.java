package com.careerpilot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, unique = true, length = 100)
    private String companyName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    @Column(name = "coding_topics", columnDefinition = "TEXT")
    private String codingTopics;

    @Column(name = "technical_topics", columnDefinition = "TEXT")
    private String technicalTopics;

    @Column(name = "aptitude_topics", columnDefinition = "TEXT")
    private String aptitudeTopics;

    @Column(name = "interview_rounds", columnDefinition = "TEXT")
    private String interviewRounds;

    @Column(name = "placement_package", length = 100)
    private String placementPackage;

    public Company() {}

    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
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
