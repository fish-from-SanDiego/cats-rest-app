package org.FishFromSanDiego.cats.controllers;

import org.FishFromSanDiego.cats.exceptions.BindingErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BindingErrorResponse handleException(BindException ex) {
        return new BindingErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(err -> new BindingErrorResponse.ErrorInfo(err.getField(), err.getDefaultMessage()))
                        .toList());
    }
}
