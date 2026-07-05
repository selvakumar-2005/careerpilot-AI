package com.careerpilot.dto;

import com.careerpilot.entity.SubmissionStatus;

import java.time.LocalDateTime;

public class CodingSubmissionDto {

    private Long id;
    private Long questionId;
    private String questionTitle;
    private String submittedCode;
    private String language;
    private SubmissionStatus status;
    private Integer score;
    private Integer attempts;
    private LocalDateTime submittedAt;

    public CodingSubmissionDto() {}

    public Long getId()                          { return id; }
    public void setId(Long v)                    { this.id = v; }
    public Long getQuestionId()                  { return questionId; }
    public void setQuestionId(Long v)            { this.questionId = v; }
    public String getQuestionTitle()             { return questionTitle; }
    public void setQuestionTitle(String v)       { this.questionTitle = v; }
    public String getSubmittedCode()             { return submittedCode; }
    public void setSubmittedCode(String v)       { this.submittedCode = v; }
    public String getLanguage()                  { return language; }
    public void setLanguage(String v)            { this.language = v; }
    public SubmissionStatus getStatus()          { return status; }
    public void setStatus(SubmissionStatus v)    { this.status = v; }
    public Integer getScore()                    { return score; }
    public void setScore(Integer v)              { this.score = v; }
    public Integer getAttempts()                 { return attempts; }
    public void setAttempts(Integer v)           { this.attempts = v; }
    public LocalDateTime getSubmittedAt()        { return submittedAt; }
    public void setSubmittedAt(LocalDateTime v)  { this.submittedAt = v; }
}
