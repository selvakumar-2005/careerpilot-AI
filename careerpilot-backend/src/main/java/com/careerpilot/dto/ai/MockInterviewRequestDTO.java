package com.careerpilot.dto.ai;

public class MockInterviewRequestDTO {
    private String jobRole;
    private String experienceLevel;
    private String company;
    private String technology;
    private String answer;
    private Integer questionNumber;
    private Integer totalQuestions;

    public MockInterviewRequestDTO() {}

    public MockInterviewRequestDTO(String jobRole, String experienceLevel, String company, String technology) {
        this.jobRole = jobRole;
        this.experienceLevel = experienceLevel;
        this.company = company;
        this.technology = technology;
    }

    public String getJobRole() { return jobRole; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getTechnology() { return technology; }
    public void setTechnology(String technology) { this.technology = technology; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Integer getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }

    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
}
