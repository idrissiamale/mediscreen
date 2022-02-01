package com.mpatient.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * ErrorResponse's role is to store custom error messages.
 */
public class ErrorResponse {
    private HttpStatus status;
    private String errorMessage;
    private List<String> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus status, String errorMessage, List<String> errors) {
        super();
        this.errorMessage = errorMessage;
        this.status = status;
        this.errors = errors;
    }

    public ErrorResponse(HttpStatus status, String errorMessage, String error) {
        super();
        this.errorMessage = errorMessage;
        this.status = status;
        errors = Collections.singletonList(error);
    }

    /**
     * Getters and setters.
     */
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
