package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MockInterviewResponseDTO {
    @JsonProperty("question")
    private String question;

    @JsonProperty("question_number")
    private Integer questionNumber;

    @JsonProperty("total_questions")
    private Integer totalQuestions;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("strengths")
    private List<String> strengths;

    @JsonProperty("weaknesses")
    private List<String> weaknesses;

    @JsonProperty("improvement_suggestions")
    private List<String> improvementSuggestions;

    @JsonProperty("feedback")
    private String feedback;

    @JsonProperty("overall_performance")
    private String overallPerformance;

    @JsonProperty("average_score")
    private Double averageScore;

    public MockInterviewResponseDTO() {}

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Integer getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }

    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public List<String> getStrengths() { return strengths; }
    public void setStrengths(List<String> strengths) { this.strengths = strengths; }

    public List<String> getWeaknesses() { return weaknesses; }
    public void setWeaknesses(List<String> weaknesses) { this.weaknesses = weaknesses; }

    public List<String> getImprovementSuggestions() { return improvementSuggestions; }
    public void setImprovementSuggestions(List<String> improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getOverallPerformance() { return overallPerformance; }
    public void setOverallPerformance(String overallPerformance) { this.overallPerformance = overallPerformance; }

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }
}
