package com.example.hotelbookingsystem.payload.roomInventory_related;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InventoryResponse(
        Long roomId,
        LocalDate date,
        Integer availableRooms,
        BigDecimal priceOverride
) {
}
