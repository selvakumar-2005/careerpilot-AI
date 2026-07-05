package com.careerpilot.dto.ai;

public class CodingAIRequestDTO {
    private String code;
    private String language;
    private String problemStatement;

    public CodingAIRequestDTO() {}
    public CodingAIRequestDTO(String code, String language, String problemStatement) {
        this.code = code;
        this.language = language;
        this.problemStatement = problemStatement;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getProblemStatement() { return problemStatement; }
    public void setProblemStatement(String problemStatement) { this.problemStatement = problemStatement; }
}
