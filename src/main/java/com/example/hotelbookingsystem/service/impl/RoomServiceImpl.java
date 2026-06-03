package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.exception.HotelNotBelongException;
import com.example.hotelbookingsystem.exception.HotelNotFoundException;
import com.example.hotelbookingsystem.exception.RoomNotFoundException;
import com.example.hotelbookingsystem.mapper.RoomMapper;
import com.example.hotelbookingsystem.payload.room_related.RoomCreateRequest;
import com.example.hotelbookingsystem.payload.room_related.RoomResponse;
import com.example.hotelbookingsystem.payload.room_related.RoomUpdateRequest;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.RoomRepository;
import com.example.hotelbookingsystem.security.SecurityUtils;
import com.example.hotelbookingsystem.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER')")
    public RoomResponse createRoom(Long hotelId, RoomCreateRequest request) {
        Hotel hotel = getHotelWithManager(hotelId);

        Room room = Room.builder()
                .roomType(request.roomType())
                .totalRooms(request.totalRooms())
                .hotel(hotel)
                .basePrice(request.basePrice())
                .description(request.description())
                .isActive(true)
                .capacity(request.capacity())
                .build();
        roomRepository.save(room);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER')")
    public RoomResponse updateRoom(Long roomId, RoomUpdateRequest request) {
        Room room = getRoomAndValidateHotelWithManager(roomId);

        room.setTotalRooms(request.totalRooms());
        room.setBasePrice(request.basePrice());
        room.setDescription(request.description());
        room.setIsActive(true);
        room.setCapacity(request.capacity());
        room.setRoomType(request.roomType());
        room.setHotel(hotelRepository.findById(request.hotelId()).orElseThrow(() -> new HotelNotFoundException("hotel not found")));
        room.setImageUrls(request.imageUrls());
        roomRepository.save(room);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("room not found"));

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomResponse> getRoomsByHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel not found"));

        return hotel.getRooms().stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER')")
    public void deleteRoom(Long roomId) {
        Room room = getRoomAndValidateHotelWithManager(roomId);

        room.setIsActive(false);
        roomRepository.save(room);
    }

    protected Room getRoomAndValidateHotelWithManager(Long roomId){
        User user = securityUtils.getCurrentUser();
        Room room =  roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("room not found"));

        if (room.getHotel().getUser().getId().equals(user.getId())) {
            throw new HotelNotBelongException("hotel not found");
        }

        return room;
    }

    private Hotel getHotelWithManager(Long hotelId) {
        User user = securityUtils.getCurrentUser();
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel not found"));

        if (hotel.getUser().getId().equals(user.getId())) {
            throw new HotelNotBelongException("hotel not found");
        }

        return hotel;
    }
}
