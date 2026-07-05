package com.careerpilot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {}

    private ApiResponse(Builder<T> builder) {
        this.success   = builder.success;
        this.message   = builder.message;
        this.data      = builder.data;
        this.timestamp = builder.timestamp;
    }

    public static <T> Builder<T> builder() { return new Builder<>(); }

    public static class Builder<T> {
        private boolean success;
        private String message;
        private T data;
        private LocalDateTime timestamp;

        public Builder<T> success(boolean v)      { this.success = v; return this; }
        public Builder<T> message(String v)       { this.message = v; return this; }
        public Builder<T> data(T v)               { this.data = v; return this; }
        public Builder<T> timestamp(LocalDateTime v) { this.timestamp = v; return this; }
        public ApiResponse<T> build()             { return new ApiResponse<>(this); }
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true).message(message).data(data)
                .timestamp(LocalDateTime.now()).build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true).message(message)
                .timestamp(LocalDateTime.now()).build();
    }

    public static <T> ApiResponse<T> failure(String message) {
        return ApiResponse.<T>builder()
                .success(false).message(message)
                .timestamp(LocalDateTime.now()).build();
    }

    public boolean isSuccess()           { return success; }
    public void setSuccess(boolean v)    { this.success = v; }
    public String getMessage()           { return message; }
    public void setMessage(String v)     { this.message = v; }
    public T getData()                   { return data; }
    public void setData(T v)             { this.data = v; }
    public LocalDateTime getTimestamp()  { return timestamp; }
    public void setTimestamp(LocalDateTime v) { this.timestamp = v; }
}
