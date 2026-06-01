package com.example.hotelbookingsystem.payload.room_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RoomUpdateRequest(
        @NotNull String roomType,
        String description,
        @NotNull @Min(1) Integer capacity,
        @NotNull @Positive BigDecimal basePrice,
        @NotNull Long hotelId,
        @NotNull @Min(1) Integer totalRooms,
        List<String> imageUrls
) {
}
