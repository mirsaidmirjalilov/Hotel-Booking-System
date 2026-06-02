package com.example.hotelbookingsystem.payload.roomInventory_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InventoryUpdateRequest(
        @NotNull @Future LocalDate date,
        @NotNull @Positive Integer availableRooms,
        @NotNull @Positive BigDecimal priceOverride
) {
}
