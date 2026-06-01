package com.example.hotelbookingsystem.payload.hotel_related;

import com.example.hotelbookingsystem.entity.Review;
import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HotelResponse(
        Long id,
        String hotelName,
        String description,
        String city,
        String address,
        String phoneNumber,
        UserResponse userResponse,
        List<Room> rooms,
        List<Review> reviews
) {
}
