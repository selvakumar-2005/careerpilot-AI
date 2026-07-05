package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CompanyPreparationResponseDTO {
    @JsonProperty("company_overview")
    private String companyOverview;
    
    @JsonProperty("hiring_process")
    private List<String> hiringProcess;
    
    @JsonProperty("technical_topics")
    private List<String> technicalTopics;
    
    @JsonProperty("coding_topics")
    private List<String> codingTopics;
    
    @JsonProperty("interview_questions")
    private List<String> interviewQuestions;
    
    @JsonProperty("hr_questions")
    private List<String> hrQuestions;
    
    @JsonProperty("preparation_strategy")
    private String preparationStrategy;
    
    @JsonProperty("expected_difficulty")
    private String expectedDifficulty;
    
    @JsonProperty("resources")
    private List<String> resources;

    public CompanyPreparationResponseDTO() {}

    public String getCompanyOverview() { return companyOverview; }
    public void setCompanyOverview(String companyOverview) { this.companyOverview = companyOverview; }
    public List<String> getHiringProcess() { return hiringProcess; }
    public void setHiringProcess(List<String> hiringProcess) { this.hiringProcess = hiringProcess; }
    public List<String> getTechnicalTopics() { return technicalTopics; }
    public void setTechnicalTopics(List<String> technicalTopics) { this.technicalTopics = technicalTopics; }
    public List<String> getCodingTopics() { return codingTopics; }
    public void setCodingTopics(List<String> codingTopics) { this.codingTopics = codingTopics; }
    public List<String> getInterviewQuestions() { return interviewQuestions; }
    public void setInterviewQuestions(List<String> interviewQuestions) { this.interviewQuestions = interviewQuestions; }
    public List<String> getHrQuestions() { return hrQuestions; }
    public void setHrQuestions(List<String> hrQuestions) { this.hrQuestions = hrQuestions; }
    public String getPreparationStrategy() { return preparationStrategy; }
    public void setPreparationStrategy(String preparationStrategy) { this.preparationStrategy = preparationStrategy; }
    public String getExpectedDifficulty() { return expectedDifficulty; }
    public void setExpectedDifficulty(String expectedDifficulty) { this.expectedDifficulty = expectedDifficulty; }
    public List<String> getResources() { return resources; }
    public void setResources(List<String> resources) { this.resources = resources; }
}
