package com.example.hotelbookingsystem.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private String errorPath;
    private String errorMessage;
    private Integer errorCode;
    private final LocalDateTime  timestamp = LocalDateTime.now();
    private final boolean success = false;

    public static ErrorResponse error(String errorPath, String errorMessage, Integer errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.errorPath = errorPath;
        response.errorMessage = errorMessage;
        response.errorCode = errorCode;
        return response;
    }
}
