package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ResumeAIResponseDTO {
    @JsonProperty("resume_score")
    private String resumeScore;
    
    @JsonProperty("ats_score")
    private String atsScore;
    
    @JsonProperty("grammar_mistakes")
    private List<String> grammarMistakes;
    
    @JsonProperty("formatting_suggestions")
    private List<String> formattingSuggestions;
    
    @JsonProperty("weak_sections")
    private List<String> weakSections;
    
    @JsonProperty("missing_skills")
    private List<String> missingSkills;
    
    @JsonProperty("projects_to_add")
    private List<String> projectsToAdd;
    
    @JsonProperty("certifications")
    private List<String> certifications;
    
    @JsonProperty("internship_suggestions")
    private List<String> internshipSuggestions;
    
    @JsonProperty("technical_improvements")
    private List<String> technicalImprovements;
    
    @JsonProperty("overall_feedback")
    private String overallFeedback;

    public ResumeAIResponseDTO() {}

    public ResumeAIResponseDTO(String resumeScore, String atsScore, List<String> grammarMistakes,
                               List<String> formattingSuggestions, List<String> weakSections,
                               List<String> missingSkills, List<String> projectsToAdd,
                               List<String> certifications, List<String> internshipSuggestions,
                               List<String> technicalImprovements, String overallFeedback) {
        this.resumeScore = resumeScore;
        this.atsScore = atsScore;
        this.grammarMistakes = grammarMistakes;
        this.formattingSuggestions = formattingSuggestions;
        this.weakSections = weakSections;
        this.missingSkills = missingSkills;
        this.projectsToAdd = projectsToAdd;
        this.certifications = certifications;
        this.internshipSuggestions = internshipSuggestions;
        this.technicalImprovements = technicalImprovements;
        this.overallFeedback = overallFeedback;
    }

    public String getResumeScore() { return resumeScore; }
    public void setResumeScore(String resumeScore) { this.resumeScore = resumeScore; }
    public String getAtsScore() { return atsScore; }
    public void setAtsScore(String atsScore) { this.atsScore = atsScore; }
    public List<String> getGrammarMistakes() { return grammarMistakes; }
    public void setGrammarMistakes(List<String> grammarMistakes) { this.grammarMistakes = grammarMistakes; }
    public List<String> getFormattingSuggestions() { return formattingSuggestions; }
    public void setFormattingSuggestions(List<String> formattingSuggestions) { this.formattingSuggestions = formattingSuggestions; }
    public List<String> getWeakSections() { return weakSections; }
    public void setWeakSections(List<String> weakSections) { this.weakSections = weakSections; }
    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }
    public List<String> getProjectsToAdd() { return projectsToAdd; }
    public void setProjectsToAdd(List<String> projectsToAdd) { this.projectsToAdd = projectsToAdd; }
    public List<String> getCertifications() { return certifications; }
    public void setCertifications(List<String> certifications) { this.certifications = certifications; }
    public List<String> getInternshipSuggestions() { return internshipSuggestions; }
    public void setInternshipSuggestions(List<String> internshipSuggestions) { this.internshipSuggestions = internshipSuggestions; }
    public List<String> getTechnicalImprovements() { return technicalImprovements; }
    public void setTechnicalImprovements(List<String> technicalImprovements) { this.technicalImprovements = technicalImprovements; }
    public String getOverallFeedback() { return overallFeedback; }
    public void setOverallFeedback(String overallFeedback) { this.overallFeedback = overallFeedback; }
}
