package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.exception.HotelNotFoundException;
import com.example.hotelbookingsystem.exception.UserNotFoundException;
import com.example.hotelbookingsystem.mapper.HotelMapper;
import com.example.hotelbookingsystem.mapper.UserMapper;
import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelRequest;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelUpdateRequest;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import com.example.hotelbookingsystem.service.HotelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public HotelResponse createHotel(Long managerId, HotelRequest request) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user with " + managerId + " not found"));

        Hotel hotel = Hotel.builder()
                .hotelName(request.hotelName())
                .description(request.description())
                .city(request.city())
                .user(user)
                .phoneNumber(request.phoneNumber())
                .city(request.city())
                .address(request.address())
                .description(request.description())
                .build();
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel, userMapper.mapToUserResponse(user));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Transactional
    public HotelResponse updateHotel(Long hotelId, Long managerId, HotelUpdateRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel with " + hotelId + " not found"));

        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user with " + managerId + " not found"));

        hotel.setHotelName(request.hotelName());
        hotel.setDescription(request.description());
        hotel.setCity(request.city());
        hotel.setAddress(request.address());
        hotel.setPhoneNumber(request.phoneNumber());
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel, userMapper.mapToUserResponse(user));
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel with " + hotelId + " not found"));

        return hotelMapper.toHotelResponse(hotel, userMapper.mapToUserResponse(hotel.getUser()));
    }

    @Override
    public Page<HotelResponse> searchHotels(String city, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByCityContainingIgnoreCaseAndActiveTrue(city, pageable);

        return hotelPage.map(hotel -> hotelMapper.toHotelResponse(hotel, userMapper.mapToUserResponse(hotel.getUser())));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<HotelResponse> getMyHotels(Long managerId) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user with " + managerId + " not found"));

        return user.getHotels().stream().map(hotel -> hotelMapper.toHotelResponse(hotel, userMapper.mapToUserResponse(user))).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Transactional
    public void deleteHotel(Long hotelId, Long managerId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel with " + hotelId + " not found"));
        hotel.setActive(false);
        hotel.setUser(null);
        hotelRepository.save(hotel);

        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user with " + managerId + " not found"));

        if (!user.getHotels().contains(hotel)) {
            throw new IllegalArgumentException("Manager with ID " + managerId + " is not assigned to this hotel.");
        }

        user.getHotels().remove(hotel);
        userRepository.save(user);
    }
}
