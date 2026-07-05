package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CareerGuidanceResponseDTO {
    @JsonProperty("suitable_job_roles")
    private List<String> suitableJobRoles;
    
    @JsonProperty("skill_gap")
    private String skillGap;
    
    @JsonProperty("learning_roadmap")
    private List<String> learningRoadmap;
    
    @JsonProperty("recommended_courses")
    private List<String> recommendedCourses;
    
    @JsonProperty("project_ideas")
    private List<String> projectIdeas;
    
    @JsonProperty("certification_suggestions")
    private List<String> certificationSuggestions;
    
    @JsonProperty("future_career_path")
    private String futureCareerPath;
    
    @JsonProperty("timeline")
    private String timeline;

    public CareerGuidanceResponseDTO() {}

    public List<String> getSuitableJobRoles() { return suitableJobRoles; }
    public void setSuitableJobRoles(List<String> suitableJobRoles) { this.suitableJobRoles = suitableJobRoles; }
    public String getSkillGap() { return skillGap; }
    public void setSkillGap(String skillGap) { this.skillGap = skillGap; }
    public List<String> getLearningRoadmap() { return learningRoadmap; }
    public void setLearningRoadmap(List<String> learningRoadmap) { this.learningRoadmap = learningRoadmap; }
    public List<String> getRecommendedCourses() { return recommendedCourses; }
    public void setRecommendedCourses(List<String> recommendedCourses) { this.recommendedCourses = recommendedCourses; }
    public List<String> getProjectIdeas() { return projectIdeas; }
    public void setProjectIdeas(List<String> projectIdeas) { this.projectIdeas = projectIdeas; }
    public List<String> getCertificationSuggestions() { return certificationSuggestions; }
    public void setCertificationSuggestions(List<String> certificationSuggestions) { this.certificationSuggestions = certificationSuggestions; }
    public String getFutureCareerPath() { return futureCareerPath; }
    public void setFutureCareerPath(String futureCareerPath) { this.futureCareerPath = futureCareerPath; }
    public String getTimeline() { return timeline; }
    public void setTimeline(String timeline) { this.timeline = timeline; }
}
