package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.payload.auth_related.AuthResponse;
import com.example.hotelbookingsystem.payload.auth_related.LoginRequest;
import com.example.hotelbookingsystem.payload.auth_related.RegisterRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface AuthService {
    AuthResponse login(@RequestBody LoginRequest loginRequest);
    AuthResponse register(@RequestBody RegisterRequest registerRequest);
}
