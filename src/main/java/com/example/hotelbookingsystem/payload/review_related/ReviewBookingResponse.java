package com.example.hotelbookingsystem.payload.review_related;

public record ReviewBookingResponse(
        Long id,
        Long bookingId,
        String roomType,
        Integer rating,
        String comment
) {
}
