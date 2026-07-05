package com.careerpilot.dto;

import com.careerpilot.entity.Difficulty;

public class CodingQuestionDto {

    private Long id;
    private String title;
    private String description;
    private Difficulty difficulty;
    private String inputFormat;
    private String outputFormat;
    private String constraints;
    private String sampleInput;
    private String sampleOutput;
    private String topic;

    public CodingQuestionDto() {}

    public Long getId()                       { return id; }
    public void setId(Long v)                 { this.id = v; }
    public String getTitle()                  { return title; }
    public void setTitle(String v)            { this.title = v; }
    public String getDescription()            { return description; }
    public void setDescription(String v)      { this.description = v; }
    public Difficulty getDifficulty()         { return difficulty; }
    public void setDifficulty(Difficulty v)   { this.difficulty = v; }
    public String getInputFormat()            { return inputFormat; }
    public void setInputFormat(String v)      { this.inputFormat = v; }
    public String getOutputFormat()           { return outputFormat; }
    public void setOutputFormat(String v)     { this.outputFormat = v; }
    public String getConstraints()            { return constraints; }
    public void setConstraints(String v)      { this.constraints = v; }
    public String getSampleInput()            { return sampleInput; }
    public void setSampleInput(String v)      { this.sampleInput = v; }
    public String getSampleOutput()           { return sampleOutput; }
    public void setSampleOutput(String v)     { this.sampleOutput = v; }
    public String getTopic()                  { return topic; }
    public void setTopic(String v)            { this.topic = v; }
}
