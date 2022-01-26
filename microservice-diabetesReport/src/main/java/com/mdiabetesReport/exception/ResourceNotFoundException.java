package com.mdiabetesReport.exception;

import org.springframework.http.HttpStatus;

/**
 * A custom exception created in order to be thrown in the controllers if the resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    private String errorCode;
    private HttpStatus status;

    public ResourceNotFoundException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    /**
     * Getters.
     */

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
