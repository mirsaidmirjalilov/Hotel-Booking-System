package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.payload.roomInventory_related.AvailabilityCheckRequest;
import com.example.hotelbookingsystem.payload.roomInventory_related.BulkInventoryRequest;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryResponse;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryUpdateRequest;

import java.time.LocalDate;
import java.util.List;

public interface RoomInventoryService {
    boolean isRoomAvailable(Long roomId,AvailabilityCheckRequest request);
    List<InventoryResponse> getDailyAvailability(Long roomId, LocalDate startDate, LocalDate endDate);
    InventoryResponse updateInventory(Long managerId, Long roomId, InventoryUpdateRequest request);
    List<InventoryResponse> bulkUpdateInventory(Long managerId, Long roomId, BulkInventoryRequest request);
    void blockInventory(Long roomId, LocalDate checkIn, LocalDate checkOut, int roomsToBlock);
    void releaseInventory(Long roomId, LocalDate checkIn, LocalDate checkOut, int roomsToRelease);
    List<InventoryResponse> initializeInventoryForRoom(Long roomId, int monthsAhead);
}
