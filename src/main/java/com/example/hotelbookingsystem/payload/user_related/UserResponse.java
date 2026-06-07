package com.example.hotelbookingsystem.payload.user_related;

import com.example.hotelbookingsystem.enums.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String fullName,
        Role role,
        String phoneNumber,
        LocalDateTime createdAt
) {
}
