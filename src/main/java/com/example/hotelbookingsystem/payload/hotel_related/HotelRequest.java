package com.example.hotelbookingsystem.payload.hotel_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record HotelRequest(
    @NotNull String hotelName,
    String description,
    @NotNull String city,
    @NotNull String address,
    @Pattern(regexp = "^\\+998(9[01345789])\\d{7}$") @NotNull String phoneNumber
) {
}
