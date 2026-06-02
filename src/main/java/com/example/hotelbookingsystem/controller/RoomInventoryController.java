package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.roomInventory_related.AvailabilityCheckRequest;
import com.example.hotelbookingsystem.payload.roomInventory_related.BulkInventoryRequest;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryResponse;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryUpdateRequest;
import com.example.hotelbookingsystem.service.RoomInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room-inventories")
public class RoomInventoryController {
    private final RoomInventoryService roomInventoryService;

    @PostMapping("/{managerId}/manager/{roomId}/room/inventory/bulk")
    public ResponseEntity<BaseResponse<List<InventoryResponse>>> bulk(@PathVariable Long roomId, @RequestBody @Valid BulkInventoryRequest request, @PathVariable Long managerId) {
        List<InventoryResponse> inventoryResponses = roomInventoryService.bulkUpdateInventory(managerId, roomId, request);

        return ResponseEntity.status(200).body(BaseResponse.ok(inventoryResponses));
    }

    @PutMapping("/{managerId}/manager/{roomId}/room/inventory/update")
    public ResponseEntity<BaseResponse<InventoryResponse>> update(@PathVariable Long managerId, @PathVariable Long roomId, @RequestBody @Valid InventoryUpdateRequest request) {
        InventoryResponse inventoryResponse = roomInventoryService.updateInventory(managerId, roomId, request);

        return ResponseEntity.status(200).body(BaseResponse.ok(inventoryResponse));
    }

    @PostMapping("/{roomId}/room/availability")
    public ResponseEntity<BaseResponse<Boolean>> checkAvailability(
            @PathVariable Long roomId,
            AvailabilityCheckRequest request
    ) {
        return roomInventoryService.isRoomAvailable(roomId, request) ? ResponseEntity.status(200).body(BaseResponse.ok()) : ResponseEntity.status(404).build();
    }

    @GetMapping("/{roomId}/room/availability")
    public ResponseEntity<BaseResponse<List<InventoryResponse>>> getAvailability(
            @PathVariable Long roomId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        List<InventoryResponse> dailyAvailability = roomInventoryService.getDailyAvailability(roomId, startDate, endDate);

        return ResponseEntity.status(200).body(BaseResponse.ok(dailyAvailability));
    }
}
