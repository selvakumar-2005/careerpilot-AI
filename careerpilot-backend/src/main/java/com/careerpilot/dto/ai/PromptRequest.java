package com.careerpilot.dto.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO used when the caller selects a named template and
 * supplies the variable content to fill into that template.
 *
 * Example:
 *   templateKey  = "RESUME_ANALYSIS"
 *   userContent  = "<raw resume text here>"
 */
public class PromptRequest {

    @NotNull(message = "Template key is required")
    @NotBlank(message = "Template key cannot be empty")
    private String templateKey;

    @NotBlank(message = "User content cannot be empty")
    private String userContent;

    /** Optional extra parameters passed through to the template (free-form). */
    private String extraParams;

    public PromptRequest() {}

    public String getTemplateKey()            { return templateKey; }
    public void   setTemplateKey(String v)    { this.templateKey = v; }
    public String getUserContent()            { return userContent; }
    public void   setUserContent(String v)    { this.userContent = v; }
    public String getExtraParams()            { return extraParams; }
    public void   setExtraParams(String v)    { this.extraParams = v; }
}
