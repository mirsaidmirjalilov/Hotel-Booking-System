package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.payload.booking_related.BookingResponse;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getRoom().getHotel().getHotelName(),
                booking.getRoom().getRoomType(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getTotalGuests(),
                booking.getTotalPrice(),
                booking.getStatus(),
                booking.getPaymentStatus(),
                booking.getBookedAt()
        );
    }
}
