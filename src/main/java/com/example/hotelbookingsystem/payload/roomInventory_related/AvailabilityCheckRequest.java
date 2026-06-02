package com.example.hotelbookingsystem.payload.roomInventory_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AvailabilityCheckRequest(
        @NotNull @Future LocalDate checkIn,
        @NotNull @Future LocalDate checkOut,
        @NotNull @Positive Integer requestedRooms
) {
}
