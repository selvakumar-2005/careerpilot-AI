package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class InterviewResponseDTO {
    @JsonProperty("technical_questions")
    private List<InterviewQuestion> technicalQuestions;
    
    @JsonProperty("hr_questions")
    private List<InterviewQuestion> hrQuestions;
    
    @JsonProperty("interview_tips")
    private List<String> interviewTips;
    
    @JsonProperty("confidence_tips")
    private List<String> confidenceTips;

    public InterviewResponseDTO() {}

    public List<InterviewQuestion> getTechnicalQuestions() { return technicalQuestions; }
    public void setTechnicalQuestions(List<InterviewQuestion> technicalQuestions) { this.technicalQuestions = technicalQuestions; }
    public List<InterviewQuestion> getHrQuestions() { return hrQuestions; }
    public void setHrQuestions(List<InterviewQuestion> hrQuestions) { this.hrQuestions = hrQuestions; }
    public List<String> getInterviewTips() { return interviewTips; }
    public void setInterviewTips(List<String> interviewTips) { this.interviewTips = interviewTips; }
    public List<String> getConfidenceTips() { return confidenceTips; }
    public void setConfidenceTips(List<String> confidenceTips) { this.confidenceTips = confidenceTips; }

    public static class InterviewQuestion {
        private String question;
        @JsonProperty("model_answer")
        private String modelAnswer;
        @JsonProperty("follow_up_questions")
        private List<String> followUpQuestions;

        public InterviewQuestion() {}
        public InterviewQuestion(String question, String modelAnswer, List<String> followUpQuestions) {
            this.question = question;
            this.modelAnswer = modelAnswer;
            this.followUpQuestions = followUpQuestions;
        }

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getModelAnswer() { return modelAnswer; }
        public void setModelAnswer(String modelAnswer) { this.modelAnswer = modelAnswer; }
        public List<String> getFollowUpQuestions() { return followUpQuestions; }
        public void setFollowUpQuestions(List<String> followUpQuestions) { this.followUpQuestions = followUpQuestions; }
    }
}
