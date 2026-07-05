package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SkillGapResponseDTO {
    @JsonProperty("matching_skills")
    private List<String> matchingSkills;

    @JsonProperty("missing_skills")
    private List<String> missingSkills;

    @JsonProperty("high_priority_skills")
    private List<String> highPrioritySkills;

    @JsonProperty("learning_order")
    private List<String> learningOrder;

    @JsonProperty("improvement_suggestions")
    private List<String> improvementSuggestions;

    @JsonProperty("timeline_estimate")
    private String timelineEstimate;

    @JsonProperty("resources_recommended")
    private List<String> resourcesRecommended;

    @JsonProperty("skill_gap_summary")
    private String skillGapSummary;

    public SkillGapResponseDTO() {}

    public List<String> getMatchingSkills() { return matchingSkills; }
    public void setMatchingSkills(List<String> matchingSkills) { this.matchingSkills = matchingSkills; }

    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }

    public List<String> getHighPrioritySkills() { return highPrioritySkills; }
    public void setHighPrioritySkills(List<String> highPrioritySkills) { this.highPrioritySkills = highPrioritySkills; }

    public List<String> getLearningOrder() { return learningOrder; }
    public void setLearningOrder(List<String> learningOrder) { this.learningOrder = learningOrder; }

    public List<String> getImprovementSuggestions() { return improvementSuggestions; }
    public void setImprovementSuggestions(List<String> improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }

    public String getTimelineEstimate() { return timelineEstimate; }
    public void setTimelineEstimate(String timelineEstimate) { this.timelineEstimate = timelineEstimate; }

    public List<String> getResourcesRecommended() { return resourcesRecommended; }
    public void setResourcesRecommended(List<String> resourcesRecommended) { this.resourcesRecommended = resourcesRecommended; }

    public String getSkillGapSummary() { return skillGapSummary; }
    public void setSkillGapSummary(String skillGapSummary) { this.skillGapSummary = skillGapSummary; }
}
