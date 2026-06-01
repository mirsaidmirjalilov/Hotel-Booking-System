package com.example.hotelbookingsystem.payload.auth_related;

import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse
{
    String message;
    String token;
    UserResponse user;
    Boolean success;
    LocalDateTime dateTime;

    private AuthResponse()
    {
        success = true;
        dateTime = LocalDateTime.now();
    }

    public AuthResponse(String message, String token, UserResponse user)
    {
        this();
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public AuthResponse(String message, UserResponse user)
    {
        this();
        this.message = message;
        this.user = user;
    }
}
