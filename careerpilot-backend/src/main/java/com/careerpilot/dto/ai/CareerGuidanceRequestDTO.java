package com.careerpilot.dto.ai;

public class CareerGuidanceRequestDTO {
    private String currentSkills;
    private String currentSemester;
    private String targetCompany;
    private String targetRole;
    private Double currentCGPA;

    public CareerGuidanceRequestDTO() {}
    public CareerGuidanceRequestDTO(String currentSkills, String currentSemester, String targetCompany, String targetRole, Double currentCGPA) {
        this.currentSkills = currentSkills;
        this.currentSemester = currentSemester;
        this.targetCompany = targetCompany;
        this.targetRole = targetRole;
        this.currentCGPA = currentCGPA;
    }

    public String getCurrentSkills() { return currentSkills; }
    public void setCurrentSkills(String currentSkills) { this.currentSkills = currentSkills; }
    public String getCurrentSemester() { return currentSemester; }
    public void setCurrentSemester(String currentSemester) { this.currentSemester = currentSemester; }
    public String getTargetCompany() { return targetCompany; }
    public void setTargetCompany(String targetCompany) { this.targetCompany = targetCompany; }
    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
    public Double getCurrentCGPA() { return currentCGPA; }
    public void setCurrentCGPA(Double currentCGPA) { this.currentCGPA = currentCGPA; }
}
