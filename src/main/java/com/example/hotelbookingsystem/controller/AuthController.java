package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.auth_related.AuthResponse;
import com.example.hotelbookingsystem.payload.auth_related.LoginRequest;
import com.example.hotelbookingsystem.payload.auth_related.RegisterRequest;
import com.example.hotelbookingsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse register = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse login = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(login);
    }
}
