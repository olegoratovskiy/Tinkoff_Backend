package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CreatingExistingEntityException extends RuntimeException {
    public CreatingExistingEntityException(String message){
        super(message);
    }
}
