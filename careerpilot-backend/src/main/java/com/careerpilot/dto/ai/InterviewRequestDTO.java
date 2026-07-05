package com.careerpilot.dto.ai;

public class InterviewRequestDTO {
    private String company;
    private String technology;
    private String difficulty;
    private String experienceLevel;

    public InterviewRequestDTO() {}
    public InterviewRequestDTO(String company, String technology, String difficulty, String experienceLevel) {
        this.company = company;
        this.technology = technology;
        this.difficulty = difficulty;
        this.experienceLevel = experienceLevel;
    }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getTechnology() { return technology; }
    public void setTechnology(String technology) { this.technology = technology; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
}
