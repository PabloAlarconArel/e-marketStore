package org.marketstore.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();


        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<Map<String, String>>
    handleCustomerExistsException(ResourceExistsException ex) {

        Map<String, String> response = new HashMap<>();

        response.put("error","conflict");
        response.put("message", ex.getMessage());


        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)

    public ResponseEntity<Map<String, String>>
    handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        Map<String, String> response = new HashMap<>();

        response.put("error", "bad_request");
        response.put("message", "El formato de la solicitud no es válido.");


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
