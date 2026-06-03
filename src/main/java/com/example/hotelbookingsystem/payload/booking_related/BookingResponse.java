package com.example.hotelbookingsystem.payload.booking_related;

import com.example.hotelbookingsystem.enums.BookingStatus;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingResponse(
        Long bookingId,
        String hotelName,
        String roomType,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Integer totalGuests,
        BigDecimal totalPrice,
        BookingStatus bookingStatus,
        PaymentStatus paymentStatus,
        LocalDateTime bookedAt
) {
}
