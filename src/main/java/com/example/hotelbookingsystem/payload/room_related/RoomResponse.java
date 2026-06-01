package com.example.hotelbookingsystem.payload.room_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RoomResponse(
        Long id,
        String roomType,
        String description,
        Integer capacity,
        BigDecimal basePrice,
        Long hotelId,
        Integer totalRooms
) {
}
