package com.careerpilot.dto.ai;

public class ResumeAIRequestDTO {
    private String resumeText;
    private String jobDescription;
    private String targetCompany;

    public ResumeAIRequestDTO() {}

    public ResumeAIRequestDTO(String resumeText, String jobDescription, String targetCompany) {
        this.resumeText = resumeText;
        this.jobDescription = jobDescription;
        this.targetCompany = targetCompany;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getTargetCompany() {
        return targetCompany;
    }

    public void setTargetCompany(String targetCompany) {
        this.targetCompany = targetCompany;
    }
}
