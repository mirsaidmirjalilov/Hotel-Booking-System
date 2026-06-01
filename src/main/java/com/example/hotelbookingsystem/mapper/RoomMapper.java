package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.payload.room_related.RoomResponse;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomResponse toRoomResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getDescription(),
                room.getCapacity(),
                room.getBasePrice(),
                room.getHotel().getId(),
                room.getTotalRooms()
        );
    }
}
