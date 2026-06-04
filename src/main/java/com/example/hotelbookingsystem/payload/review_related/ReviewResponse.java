package com.example.hotelbookingsystem.payload.review_related;

public record ReviewResponse(
        Long id,
        Long bookingId,
        Long hotelId,
        String hotelName,
        String roomType,
        Integer rating,
        String comment
) {
}
