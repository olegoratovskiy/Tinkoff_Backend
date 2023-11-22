package com.example.demo.exceptions.handlers;

import com.example.demo.exceptions.CreatingExistingEntityException;
import com.example.demo.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CreatingExistingEntityExceptionHandler {

    @ExceptionHandler(CreatingExistingEntityException.class)
    public ResponseEntity<Response> handleException(CreatingExistingEntityException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
