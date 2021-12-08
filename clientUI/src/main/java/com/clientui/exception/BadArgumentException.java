package com.clientui.exception;

import org.springframework.http.HttpStatus;

/**
 * A custom exception created in order to be thrown in the controllers if the resource already exists or if incorrect input arguments were passed.
 */
public class BadArgumentException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Long resourceId;
    private String errorCode;
    private HttpStatus status;

    public BadArgumentException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    /**
     * Getters and setters.
     */
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
