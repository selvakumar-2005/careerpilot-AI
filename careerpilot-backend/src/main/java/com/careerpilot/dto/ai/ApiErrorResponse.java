package com.careerpilot.dto.ai;

import java.time.LocalDateTime;

/**
 * Structured error payload returned by the AI layer when the
 * Gemini call fails (invalid key, timeout, rate-limit, etc.).
 * Deliberately separate from the generic ApiResponse so that
 * AI error details are never mixed with auth/validation errors.
 */
public class ApiErrorResponse {

    private String        errorCode;
    private String        message;
    private int           httpStatus;
    private LocalDateTime timestamp;

    public ApiErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(String errorCode, String message, int httpStatus) {
        this.errorCode  = errorCode;
        this.message    = message;
        this.httpStatus = httpStatus;
        this.timestamp  = LocalDateTime.now();
    }

    // ── Gemini-specific error codes ─────────────────────────────────────────
    public static final String INVALID_API_KEY  = "GEMINI_INVALID_API_KEY";
    public static final String RATE_LIMIT       = "GEMINI_RATE_LIMIT";
    public static final String TIMEOUT          = "GEMINI_TIMEOUT";
    public static final String EMPTY_RESPONSE   = "GEMINI_EMPTY_RESPONSE";
    public static final String HTTP_ERROR       = "GEMINI_HTTP_ERROR";
    public static final String NETWORK_ERROR    = "GEMINI_NETWORK_ERROR";
    public static final String PARSE_ERROR      = "GEMINI_PARSE_ERROR";

    public String        getErrorCode()           { return errorCode; }
    public void          setErrorCode(String v)   { this.errorCode = v; }
    public String        getMessage()             { return message; }
    public void          setMessage(String v)     { this.message = v; }
    public int           getHttpStatus()          { return httpStatus; }
    public void          setHttpStatus(int v)     { this.httpStatus = v; }
    public LocalDateTime getTimestamp()           { return timestamp; }
    public void          setTimestamp(LocalDateTime v) { this.timestamp = v; }
}
