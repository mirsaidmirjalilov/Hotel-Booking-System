package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.payload.room_related.RoomCreateRequest;
import com.example.hotelbookingsystem.payload.room_related.RoomResponse;
import com.example.hotelbookingsystem.payload.room_related.RoomUpdateRequest;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(Long managerId, Long hotelId, RoomCreateRequest request);
    RoomResponse updateRoom(Long managerId, Long roomId, RoomUpdateRequest request);
    RoomResponse getRoomById(Long roomId);
    List<RoomResponse> getRoomsByHotel(Long hotelId);
    void deleteRoom(Long managerId, Long roomId);
}
