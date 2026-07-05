package com.careerpilot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubmitCodeRequest {

    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotBlank(message = "Code cannot be empty")
    private String submittedCode;

    @NotBlank(message = "Language is required")
    private String language;

    public SubmitCodeRequest() {}

    public Long getQuestionId()             { return questionId; }
    public void setQuestionId(Long v)       { this.questionId = v; }
    public String getSubmittedCode()        { return submittedCode; }
    public void setSubmittedCode(String v)  { this.submittedCode = v; }
    public String getLanguage()             { return language; }
    public void setLanguage(String v)       { this.language = v; }
}
