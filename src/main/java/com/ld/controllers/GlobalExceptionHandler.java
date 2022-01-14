package com.ld.controllers;

import com.ld.error_handling.ErrorDetails;
import com.ld.error_handling.exceptions.AccessDeniedException;
import com.ld.error_handling.exceptions.EntityNotFoundException;
import com.ld.error_handling.exceptions.UserAlreadyExistsException;
import com.ld.error_handling.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDetails> nullPointer(NullPointerException ex, WebRequest request) {
        return getResponseEntity(request, ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> notFound(EntityNotFoundException ex, WebRequest request) {
        return getResponseEntity(request, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> accessDenied(AccessDeniedException ex, WebRequest request) {
        return getResponseEntity(request, ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> userAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        return getResponseEntity(request, ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFound(UserNotFoundException ex, WebRequest request) {
        return getResponseEntity(request, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorDetails> getResponseEntity(WebRequest request, String message, HttpStatus status) {
        ErrorDetails body = new ErrorDetails(LocalDate.now(), message, request.getDescription(false));
        return new ResponseEntity<>(body, status);
    }
}
