package com.ld.controllers;

import com.ld.error_handling.exceptions.AccessDeniedException;
import com.ld.error_handling.exceptions.EntityNotFoundException;
import com.ld.error_handling.exceptions.UserAlreadyExistsException;
import com.ld.error_handling.exceptions.UserNotFoundException;
import com.ld.validation.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public Response nullPointer(NullPointerException ex) {
        return getResponseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Response notFound(EntityNotFoundException ex) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Response accessDenied(AccessDeniedException ex) {
        return getResponseEntity(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public Response userAlreadyExists(UserAlreadyExistsException ex) {
        return getResponseEntity(ex, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Response userNotFound(UserNotFoundException ex) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public Response global(Exception ex) {
        return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <E extends Exception> Response getResponseEntity(E exception, HttpStatus status) {
        log.error("ALERT: exception handled - {}", exception.getMessage());
        return Response.error(status, exception.getMessage());
    }
}
