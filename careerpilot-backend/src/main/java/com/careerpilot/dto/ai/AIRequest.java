package com.careerpilot.dto.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Generic AI request DTO.
 * Every AI endpoint that accepts a raw prompt from the client uses this.
 */
public class AIRequest {

    @NotBlank(message = "Prompt cannot be empty")
    @Size(max = 10000, message = "Prompt must not exceed 10000 characters")
    private String prompt;

    /** Optional context hint so the service can select the right template type. */
    private String context;

    public AIRequest() {}

    public AIRequest(String prompt, String context) {
        this.prompt  = prompt;
        this.context = context;
    }

    public String getPrompt()          { return prompt; }
    public void   setPrompt(String v)  { this.prompt = v; }
    public String getContext()         { return context; }
    public void   setContext(String v) { this.context = v; }
}
