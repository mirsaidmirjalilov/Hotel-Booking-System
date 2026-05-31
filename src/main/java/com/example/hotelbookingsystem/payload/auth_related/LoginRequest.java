package com.example.hotelbookingsystem.payload.auth_related;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @Email @NotNull String email,
        @NotNull String password
) {
}
