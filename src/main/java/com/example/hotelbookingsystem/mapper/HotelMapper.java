package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class HotelMapper {
    public HotelResponse toHotelResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getDescription(),
                hotel.getCity(),
                hotel.getAddress(),
                hotel.getPhoneNumber(),
                hotel.getUser().getId()
        );
    }
}
