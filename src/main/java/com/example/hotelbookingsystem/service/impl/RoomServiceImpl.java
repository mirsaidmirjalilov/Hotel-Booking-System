package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.exception.HotelNotBelongException;
import com.example.hotelbookingsystem.exception.HotelNotFoundException;
import com.example.hotelbookingsystem.exception.RoomNotFoundException;
import com.example.hotelbookingsystem.exception.UserNotFoundException;
import com.example.hotelbookingsystem.mapper.HotelMapper;
import com.example.hotelbookingsystem.mapper.RoomMapper;
import com.example.hotelbookingsystem.mapper.UserMapper;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.room_related.RoomCreateRequest;
import com.example.hotelbookingsystem.payload.room_related.RoomResponse;
import com.example.hotelbookingsystem.payload.room_related.RoomUpdateRequest;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.RoomRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import com.example.hotelbookingsystem.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final HotelMapper hotelMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER')")
    public RoomResponse createRoom(Long managerId, Long hotelId, RoomCreateRequest request) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel not found"));

        if (!user.getHotels().contains(hotel)) {
            throw new HotelNotBelongException("this hotel doesn't belong to this user");
        }

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
    public RoomResponse updateRoom(Long managerId, Long roomId, RoomUpdateRequest request) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("room not found"));

        if (!user.getHotels().contains(room.getHotel())) {
            throw new HotelNotBelongException("this hotel doesn't belong to this user");
        }

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
    public void deleteRoom(Long managerId, Long roomId) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("room not found"));

        if (!user.getHotels().contains(room.getHotel())) {
            throw new HotelNotBelongException("this hotel doesn't belong to this user");
        }

        room.setIsActive(false);
        roomRepository.save(room);
    }
}
