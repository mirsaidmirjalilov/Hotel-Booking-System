package com.example.hotelbookingsystem.payload.user_related;

import com.example.hotelbookingsystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserCreateRequest(
        @Email @NotNull String email,
        @NotNull String password,
        @NotNull String fullName,
        @NotNull @Pattern(regexp = "^\\+998(9[01345789])\\d{7}$") String phoneNumber,
        @Enumerated(EnumType.STRING) Role role
) {
}
