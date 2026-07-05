package com.careerpilot.dto.ai;

/**
 * Response DTO for template-based prompt calls.
 * Wraps the AIResponse and echoes back which template was used.
 */
public class PromptResponse {

    private String     templateKey;
    private AIResponse aiResponse;

    public PromptResponse() {}

    public PromptResponse(String templateKey, AIResponse aiResponse) {
        this.templateKey = templateKey;
        this.aiResponse  = aiResponse;
    }

    public String     getTemplateKey()           { return templateKey; }
    public void       setTemplateKey(String v)   { this.templateKey = v; }
    public AIResponse getAiResponse()            { return aiResponse; }
    public void       setAiResponse(AIResponse v){ this.aiResponse = v; }
}
