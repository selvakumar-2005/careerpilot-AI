package com.careerpilot.exception.ai;

/**
 * Thrown by GeminiHttpClient for any Gemini API failure.
 * The errorCode maps to ApiErrorResponse constants.
 * retryable=true means the caller may retry the request.
 */
public class GeminiException extends RuntimeException {

    private final String  errorCode;
    private final boolean retryable;

    public GeminiException(String message, String errorCode, boolean retryable) {
        super(message);
        this.errorCode = errorCode;
        this.retryable = retryable;
    }

    public String  getErrorCode()  { return errorCode; }
    public boolean isRetryable()   { return retryable; }
}
