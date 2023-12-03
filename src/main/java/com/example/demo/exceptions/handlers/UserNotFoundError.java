package com.example.demo.exceptions.handlers;


import lombok.Data;

@Data
public class UserNotFoundError extends Exception {
    public UserNotFoundError(String message) {
        super(message);
    }
}
