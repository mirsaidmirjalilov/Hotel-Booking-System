package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.room_related.RoomCreateRequest;
import com.example.hotelbookingsystem.payload.room_related.RoomResponse;
import com.example.hotelbookingsystem.payload.room_related.RoomUpdateRequest;
import com.example.hotelbookingsystem.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/{managerId}/{hotelId}/create")
    public ResponseEntity<BaseResponse<RoomResponse>> createRoom(@RequestBody @Valid RoomCreateRequest request, @PathVariable Long managerId, @PathVariable Long hotelId) {
        RoomResponse room = roomService.createRoom(managerId, hotelId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(room));
    }

    @PutMapping("/{managerId}/{hotelId}/update")
    ResponseEntity<BaseResponse<RoomResponse>> updateRoom(@RequestBody @Valid RoomUpdateRequest request, @PathVariable Long managerId, @PathVariable Long hotelId) {
        RoomResponse room = roomService.updateRoom(managerId, hotelId, request);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(room));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<BaseResponse<RoomResponse>> getRoom(@PathVariable Long roomId) {
        RoomResponse room = roomService.getRoomById(roomId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(room));
    }

    @DeleteMapping("/{managerId}/{roomId}")
    public void deleteRoom(@PathVariable Long roomId, @PathVariable Long managerId) {
        roomService.deleteRoom(managerId,roomId);
    }
}
