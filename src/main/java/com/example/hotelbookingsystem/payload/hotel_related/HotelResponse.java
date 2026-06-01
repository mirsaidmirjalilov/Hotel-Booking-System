package com.example.hotelbookingsystem.payload.hotel_related;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HotelResponse(
        Long id,
        String hotelName,
        String description,
        String city,
        String address,
        String phoneNumber,
        Long userId
) {
}
