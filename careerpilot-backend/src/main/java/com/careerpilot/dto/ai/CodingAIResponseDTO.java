package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CodingAIResponseDTO {
    @JsonProperty("bug_detection")
    private List<String> bugDetection;
    
    @JsonProperty("syntax_issues")
    private List<String> syntaxIssues;
    
    @JsonProperty("logical_errors")
    private List<String> logicalErrors;
    
    @JsonProperty("time_complexity")
    private String timeComplexity;
    
    @JsonProperty("space_complexity")
    private String spaceComplexity;
    
    @JsonProperty("optimized_solution")
    private String optimizedSolution;
    
    @JsonProperty("clean_code_suggestions")
    private List<String> cleanCodeSuggestions;
    
    @JsonProperty("interview_tips")
    private List<String> interviewTips;
    
    @JsonProperty("overall_verdict")
    private String overallVerdict;

    public CodingAIResponseDTO() {}

    public List<String> getBugDetection() { return bugDetection; }
    public void setBugDetection(List<String> bugDetection) { this.bugDetection = bugDetection; }
    public List<String> getSyntaxIssues() { return syntaxIssues; }
    public void setSyntaxIssues(List<String> syntaxIssues) { this.syntaxIssues = syntaxIssues; }
    public List<String> getLogicalErrors() { return logicalErrors; }
    public void setLogicalErrors(List<String> logicalErrors) { this.logicalErrors = logicalErrors; }
    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }
    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }
    public String getOptimizedSolution() { return optimizedSolution; }
    public void setOptimizedSolution(String optimizedSolution) { this.optimizedSolution = optimizedSolution; }
    public List<String> getCleanCodeSuggestions() { return cleanCodeSuggestions; }
    public void setCleanCodeSuggestions(List<String> cleanCodeSuggestions) { this.cleanCodeSuggestions = cleanCodeSuggestions; }
    public List<String> getInterviewTips() { return interviewTips; }
    public void setInterviewTips(List<String> interviewTips) { this.interviewTips = interviewTips; }
    public String getOverallVerdict() { return overallVerdict; }
    public void setOverallVerdict(String overallVerdict) { this.overallVerdict = overallVerdict; }
}
