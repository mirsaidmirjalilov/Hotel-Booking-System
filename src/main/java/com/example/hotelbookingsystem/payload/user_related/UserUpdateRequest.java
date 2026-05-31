package com.example.hotelbookingsystem.payload.user_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserUpdateRequest(
        @NotNull String fullName,
        @NotNull @Pattern(regexp = "^\\+998(9[01345789])\\d{7}$") String phoneNumber
) {
}
