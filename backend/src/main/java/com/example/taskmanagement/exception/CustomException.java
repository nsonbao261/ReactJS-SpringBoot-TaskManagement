package com.example.taskmanagement.exception;

import lombok.Data;
import lombok.AllArgsConstructor;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
