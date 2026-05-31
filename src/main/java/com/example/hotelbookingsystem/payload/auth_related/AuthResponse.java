package com.example.hotelbookingsystem.payload.auth_related;

import com.example.hotelbookingsystem.payload.user_related.UserResponse;

import java.time.LocalDateTime;

public class AuthResponse
{
    String message;
    String token;
    UserResponse user;
    Boolean success;
    LocalDateTime dateTime;

    private  AuthResponse()
    {
        success = true;
        dateTime = LocalDateTime.now();
    }

    public AuthResponse(String message, String token, UserResponse user)
    {
        new AuthResponse();
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public AuthResponse(String message, UserResponse user)
    {
        new AuthResponse();
        this.message = message;
        this.user = user;
    }
}
