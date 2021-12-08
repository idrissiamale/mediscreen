package com.clientui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * ControllerAdvisor is used to centralize and to provide global exceptions handling for the controllers.
 * With this class we can easily spread our custom exceptions in the com.clientui.controller classes.
 *
 * @see com.openclassrooms.SafetyNet.Alerts.exception.BadArgumentException
 * @see com.openclassrooms.SafetyNet.Alerts.exception.ResourceNotFoundException
 * @see com.openclassrooms.SafetyNet.Alerts.exception.ErrorResponse
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleAllExceptions(HttpServletRequest req, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("Technical Error");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setRequestURL(req.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> ResourceNotFoundError(HttpServletRequest req, ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getStatus());
        errorResponse.setErrorCode(ex.getErrorCode());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setRequestURL(req.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }


    @ExceptionHandler(BadArgumentException.class)
    public ResponseEntity<ErrorResponse> BadArgumentError(HttpServletRequest req, BadArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getStatus());
        errorResponse.setErrorCode(ex.getErrorCode());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setRequestURL(req.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
