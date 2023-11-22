package com.example.demo.exceptions.handlers;

import com.example.demo.exceptions.CreatingExistingEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CreatingExistingEntityExceptionHandler {

    @ExceptionHandler(CreatingExistingEntityException.class)
    public ResponseEntity<String> handleException(CreatingExistingEntityException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
