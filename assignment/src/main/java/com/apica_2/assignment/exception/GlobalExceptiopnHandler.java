package com.apica_2.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.apica_2.assignment.response.ErrorResponse;



import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptiopnHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "FIELD ERRORS");
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        errorResponse.setFieldErrors(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
