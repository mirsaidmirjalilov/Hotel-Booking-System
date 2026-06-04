package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.enums.BookingStatus;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import com.example.hotelbookingsystem.exception.BookingNotFoundException;
import com.example.hotelbookingsystem.mapper.BookingMapper;
import com.example.hotelbookingsystem.payload.booking_related.BookingCreateRequest;
import com.example.hotelbookingsystem.payload.booking_related.BookingResponse;
import com.example.hotelbookingsystem.repository.BookingRepository;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.RoomRepository;
import com.example.hotelbookingsystem.security.SecurityUtils;
import com.example.hotelbookingsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomServiceImpl roomService;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    @CacheEvict(value = {"userBookings", "hotelBookings", "bookings"}, allEntries = true)
    public BookingResponse createBooking(BookingCreateRequest payload) {
        Room room = roomService.getRoomAndValidateHotelWithManager(payload.roomId());

        BigDecimal totalPrice = room.getBasePrice().multiply(BigDecimal.valueOf(payload.totalGuests()));

        Booking booking = Booking.builder()
                .bookedAt(LocalDateTime.now())
                .user(SecurityUtils.getCurrentUser())
                .checkIn(payload.checkIn().atStartOfDay())
                .checkOut(payload.checkOut().atStartOfDay())
                .room(room)
                .status(BookingStatus.PENDING)
                .totalGuests(payload.totalGuests())
                .totalPrice(totalPrice)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        room.getBooking().add(booking);

        bookingRepository.save(booking);
        roomRepository.save(room);

        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    @Cacheable(value = "bookings", key = "#bookingId")
    public BookingResponse getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("booking not found"));

        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    @Cacheable(value = "userBookings", key = "T(com.example.hotelbookingsystem.security.SecurityUtils).getCurrentUser().getId()")
    public List<BookingResponse> getUserBookings() {
        User user = SecurityUtils.getCurrentUser();

        return user.getBooking().stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"userBookings", "hotelBookings", "bookings"}, allEntries = true)
    public void cancelBooking(Long bookingId) {
        Booking booking = getBookingAndValidateUser(bookingId);

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());

        bookingRepository.save(booking);
    }

    @Override
    @Cacheable(value = "hotelBookings", key = "#hotelId + '_' + #startDate + '_' + #endDate")
    public List<BookingResponse> getHotelBookings(Long hotelId, LocalDate startDate, LocalDate endDate) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new BookingNotFoundException("Hotel not found with id: " + hotelId));

        List<Room> rooms = hotel.getRooms();

        return rooms.stream()
                .flatMap(room -> room.getBooking().stream())
                .filter(booking -> isBookingInDateRange(booking, startDate, endDate))
                .map(bookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"userBookings", "hotelBookings", "bookings"}, allEntries = true)
    public void updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        Booking booking = getBookingAndValidateUser(bookingId);

        booking.setStatus(newStatus);
        bookingRepository.save(booking);
    }

    private Booking getBookingAndValidateUser(Long bookingId) {
        User user = SecurityUtils.getCurrentUser();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("booking not found"));

        if (!user.getId().equals(booking.getUser().getId())) {
            throw new BookingNotFoundException("booking not related to this user");
        }

        return booking;
    }

    private boolean isBookingInDateRange(Booking booking, LocalDate startDate, LocalDate endDate) {
        if (booking == null || booking.getCheckIn() == null || booking.getCheckOut() == null) {
            return false;
        }

        return !booking.getCheckIn().isBefore(startDate.atStartOfDay()) &&
                !booking.getCheckOut().isAfter(endDate.atStartOfDay());
    }
}