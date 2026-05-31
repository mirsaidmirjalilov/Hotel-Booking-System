package com.example.hotelbookingsystem.payload.auth_related;

import com.example.hotelbookingsystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @Email
        @NotNull
        String email,

        @NotNull
        String password,

        @NotNull
        String fullName,

        @NotNull
        @Pattern(regexp = "^\\+998(9[01345789])\\d{7}$")
        String phoneNumber,

        Role role
) {

}
