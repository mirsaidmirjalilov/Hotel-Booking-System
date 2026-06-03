package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.enums.BookingStatus;
import com.example.hotelbookingsystem.payload.booking_related.BookingCreateRequest;
import com.example.hotelbookingsystem.payload.booking_related.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingCreateRequest payload);

    BookingResponse getBookingById(Long bookingId);

    List<BookingResponse> getUserBookings();

    void cancelBooking(Long bookingId);

    List<BookingResponse> getHotelBookings(Long hotelId, LocalDate startDate, LocalDate endDate);

    void updateBookingStatus(Long bookingId, BookingStatus newStatus);
}
