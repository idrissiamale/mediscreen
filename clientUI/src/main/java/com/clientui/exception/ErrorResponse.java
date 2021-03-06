package com.clientui.exception;

import org.springframework.http.HttpStatus;

/**
 * ErrorResponse's role is to store custom error messages.
 */
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private String requestURL;
    private HttpStatus status;

    public ErrorResponse() {
    }

    /**
     * Getters and setters.
     */
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
