package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.exception.HotelNotBelongException;
import com.example.hotelbookingsystem.exception.HotelNotFoundException;
import com.example.hotelbookingsystem.exception.UserNotFoundException;
import com.example.hotelbookingsystem.mapper.HotelMapper;
import com.example.hotelbookingsystem.payload.hotel_related.HotelCreateRequest;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelUpdateRequest;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import com.example.hotelbookingsystem.security.SecurityUtils;
import com.example.hotelbookingsystem.service.HotelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final UserRepository userRepository;
    private final SecurityUtils  securityUtils;

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public HotelResponse createHotel(HotelCreateRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Hotel hotel = Hotel.builder()
                .hotelName(request.hotelName())
                .description(request.description())
                .user(user)
                .phoneNumber(request.phoneNumber())
                .city(request.city())
                .address(request.address())
                .isActive(true)
                .build();
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Transactional
    public HotelResponse updateHotel(Long hotelId, HotelUpdateRequest request) {
        Hotel hotel = getHotelAndValidateManager(hotelId);

        hotel.setHotelName(request.hotelName());
        hotel.setDescription(request.description());
        hotel.setCity(request.city());
        hotel.setAddress(request.address());
        hotel.setPhoneNumber(request.phoneNumber());
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("hotel with " + hotelId + " not found"));

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public Page<HotelResponse> searchHotels(String city, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByCityContainingIgnoreCaseAndActiveTrue(city, pageable);

        return hotelPage.map(hotelMapper::toHotelResponse);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER')")
    public List<HotelResponse> getMyHotels(Long managerId) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user with " + managerId + " not found"));

        return user.getHotels().stream().map(hotelMapper::toHotelResponse).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Transactional
    public void deleteHotel(Long hotelId) {
        Hotel hotel = getHotelAndValidateManager(hotelId);

        hotel.setActive(false);
        hotel.setUser(null);
        hotelRepository.save(hotel);

        hotel.getUser().getHotels().remove(hotel);
        userRepository.save(hotel.getUser());
    }

    private Hotel getHotelAndValidateManager(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        User user = securityUtils.getCurrentUser();

        if (hotel.getUser().getId().equals(user.getId())) {
            throw new HotelNotBelongException("hotel not found");
        }

        return hotel;
    }
}
