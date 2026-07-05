package com.careerpilot.dto.ai;

/**
 * Generic AI response DTO returned to every caller.
 * Contains the cleaned text from the LLM and optional metadata.
 */
public class AIResponse {

    private String text;
    private String model;
    private long   processingTimeMs;

    public AIResponse() {}

    public AIResponse(String text, String model, long processingTimeMs) {
        this.text              = text;
        this.model             = model;
        this.processingTimeMs  = processingTimeMs;
    }

    public String getText()                   { return text; }
    public void   setText(String v)           { this.text = v; }
    public String getModel()                  { return model; }
    public void   setModel(String v)          { this.model = v; }
    public long   getProcessingTimeMs()       { return processingTimeMs; }
    public void   setProcessingTimeMs(long v) { this.processingTimeMs = v; }
}
