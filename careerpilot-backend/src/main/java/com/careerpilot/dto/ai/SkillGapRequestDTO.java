package com.careerpilot.dto.ai;

public class SkillGapRequestDTO {
    private String currentSkills;
    private String targetRole;
    private String industry;
    private String experienceLevel;
    private String yearsOfExperience;

    public SkillGapRequestDTO() {}

    public SkillGapRequestDTO(String currentSkills, String targetRole, String industry, String experienceLevel, String yearsOfExperience) {
        this.currentSkills = currentSkills;
        this.targetRole = targetRole;
        this.industry = industry;
        this.experienceLevel = experienceLevel;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getCurrentSkills() { return currentSkills; }
    public void setCurrentSkills(String currentSkills) { this.currentSkills = currentSkills; }

    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(String yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}
