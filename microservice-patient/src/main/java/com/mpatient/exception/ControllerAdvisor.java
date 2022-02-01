package com.mpatient.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ControllerAdvisor is used to centralize and to provide global exceptions handling for the controllers.
 * With this class we can easily spread our custom exceptions in the com.clientui.controller classes.
 *
 * @see com.mpatient.exception.ResourceNotFoundException
 * @see com.mpatient.exception.ErrorResponse
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errorList);
        return handleExceptionInternal(
                ex, errorResponse, headers, errorResponse.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleAllExceptions(WebRequest request, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> ResourceNotFoundError(WebRequest request, ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getStatus());
        errorResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
