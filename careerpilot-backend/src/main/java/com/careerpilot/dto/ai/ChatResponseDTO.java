package com.careerpilot.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatResponseDTO {
    @JsonProperty("message_id")
    private Long messageId;

    private String response;

    @JsonProperty("user_message")
    private String userMessage;

    private Long timestamp;

    public ChatResponseDTO() {}
    public ChatResponseDTO(Long messageId, String response, String userMessage, Long timestamp) {
        this.messageId = messageId;
        this.response = response;
        this.userMessage = userMessage;
        this.timestamp = timestamp;
    }

    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}
