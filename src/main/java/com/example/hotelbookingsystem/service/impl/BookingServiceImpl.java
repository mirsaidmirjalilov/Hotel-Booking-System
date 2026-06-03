package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.enums.BookingStatus;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import com.example.hotelbookingsystem.mapper.BookingMapper;
import com.example.hotelbookingsystem.payload.booking_related.BookingCreateRequest;
import com.example.hotelbookingsystem.payload.booking_related.BookingResponse;
import com.example.hotelbookingsystem.repository.BookingRepository;
import com.example.hotelbookingsystem.security.SecurityUtils;
import com.example.hotelbookingsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final SecurityUtils  securityUtils;
    private final RoomServiceImpl roomService;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingCreateRequest payload) {
        Room room = roomService.getRoomAndValidateHotelWithManager(payload.roomId());

        Booking.builder()
                .bookedAt(LocalDateTime.now())
                .user(securityUtils.getCurrentUser())
                .checkIn(payload.checkIn().atStartOfDay())
                .checkOut(payload.checkOut().atStartOfDay())
                .room(room)
                .status(BookingStatus.PENDING)
                .totalGuests(payload.totalGuests())
                .totalPrice()
                .build()
    }

    @Override
    public BookingResponse getBookingById(Long bookingId) {
        return null;
    }

    @Override
    public List<BookingResponse> getUserBookings(Long userId) {
        return List.of();
    }

    @Override
    public void cancelBooking(Long bookingId) {

    }

    @Override
    public List<BookingResponse> getHotelBookings(Long hotelId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public void updateBookingStatus(Long bookingId, BookingStatus newStatus) {

    }


}
