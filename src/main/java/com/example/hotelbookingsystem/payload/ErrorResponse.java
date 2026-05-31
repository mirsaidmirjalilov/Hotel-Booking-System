package com.example.hotelbookingsystem.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    String errorPath;
    String errorMessage;
    Integer errorCode;
    LocalDateTime timestamp;
    Boolean success;

    private ErrorResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = false; // default for error responses
    }


    public static ErrorResponse error(String errorPath, String errorMessage, Integer errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.errorPath = errorPath;
        response.errorMessage = errorMessage;
        response.errorCode = errorCode;
        return response;
    }
}
