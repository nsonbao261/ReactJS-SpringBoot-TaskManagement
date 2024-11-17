package com.example.taskmanagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.taskmanagement.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleInternalException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError()
                .body(new ApiResponse("Internal Server Error", null));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(CustomException e) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse(e.getMessage(), null));
    }
}
