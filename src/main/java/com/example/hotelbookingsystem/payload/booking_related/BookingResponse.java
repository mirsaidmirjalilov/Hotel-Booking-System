package com.example.hotelbookingsystem.payload.booking_related;

import com.example.hotelbookingsystem.enums.BookingStatus;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingResponse(
        Long bookingId,
        String hotelName,
        String roomType,
        LocalDate checkIn,
        LocalDate checkOut,
        Integer totalGuests,
        BigDecimal totalPrice,
        BookingStatus bookingStatus,
        PaymentStatus paymentStatus,
        LocalDate bookedAt
) {
}
