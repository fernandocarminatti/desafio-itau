package com.challenge.Challenge.ExceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Exception> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Validation Fail: ");
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessage.append("Rejected value: ")
                    .append(fieldError.getRejectedValue())
                    .append(" for field: ")
                    .append(fieldError.getField())
                    .append(". ")
                    .append(fieldError.getDefaultMessage());
        });
        logger.error(errorMessage.toString());
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Exception> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("Malformed JSON format: " + ex.getMessage());
        return ResponseEntity.badRequest().build();
    }
}