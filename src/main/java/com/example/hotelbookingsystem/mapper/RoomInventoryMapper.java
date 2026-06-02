package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.RoomInventory;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryResponse;
import org.springframework.stereotype.Component;

@Component
public class RoomInventoryMapper {
    public InventoryResponse toRoomInventoryResponse(RoomInventory roomInventory) {
        return new InventoryResponse(
                roomInventory.getId(),
                roomInventory.getDate(),
                roomInventory.getAvailableRooms(),
                null
        );
    }
}
