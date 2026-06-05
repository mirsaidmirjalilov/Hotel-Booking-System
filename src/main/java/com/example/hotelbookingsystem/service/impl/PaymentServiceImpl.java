package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.entity.Hotel;
import com.example.hotelbookingsystem.entity.Payment;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import com.example.hotelbookingsystem.exception.BookingNotFoundException;
import com.example.hotelbookingsystem.exception.PaymentNotFoundException;
import com.example.hotelbookingsystem.mapper.PaymentMapper;
import com.example.hotelbookingsystem.payload.payment_related.PaymentMockRequest;
import com.example.hotelbookingsystem.payload.payment_related.PaymentResponse;
import com.example.hotelbookingsystem.repository.BookingRepository;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.PaymentRepository;
import com.example.hotelbookingsystem.security.SecurityUtils;
import com.example.hotelbookingsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;
    private final RoomInventoryServiceImpl roomInventoryService;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    @CacheEvict(value = {"userPayments", "hotelPayments"}, allEntries = true)
    public PaymentResponse createPayment(PaymentMockRequest paymentMockRequest) {
        User currentUser = SecurityUtils.getCurrentUser();
        Booking booking = bookingRepository.findByIdAndUserId(paymentMockRequest.booingId(), currentUser.getId()).orElseThrow(() -> new BookingNotFoundException("booking id not found"));

        if (booking.getPaymentStatus() == PaymentStatus.PAID){
            throw new RuntimeException("payment status is PAID");
        }
        if (booking.getPaymentStatus() !=  PaymentStatus.PENDING) {
            throw new RuntimeException("payment cannot be paid in current status");
        }

        Payment payment = Payment.builder()
                .booking(booking)
                .paymentStatus(PaymentStatus.PAID)
                .currency(paymentMockRequest.currency())
                .paymentStatus(paymentMockRequest.success() ? PaymentStatus.PAID : PaymentStatus.CANCELLED)
                .paidAt(paymentMockRequest.success() ? LocalDateTime.now() : null)
                .amount(booking.getTotalPrice())
                .build();
        paymentRepository.save(payment);

        if (paymentMockRequest.success()){
            booking.setPaymentStatus(PaymentStatus.PAID);
            booking.setPayment(payment);
            bookingRepository.save(booking);

            roomInventoryService.blockInventory(
                    booking.getRoom().getId(),
                    booking.getCheckIn().toLocalDate(),
                    booking.getCheckOut().toLocalDate(),
                    1
            );
        }else {
            booking.setPaymentStatus(PaymentStatus.CANCELLED);
            bookingRepository.save(booking);
        }

        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"userPayments", "hotelPayments"}, allEntries = true)
    public PaymentResponse cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("booking id not found"));

        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        payment.setBooking(null);
        paymentRepository.save(payment);

        Booking booking = bookingRepository.findById(payment.getBooking().getId()).orElseThrow(() -> new BookingNotFoundException("booking id not found"));
        booking.setPayment(null);
        booking.setPaymentStatus(PaymentStatus.CANCELLED);
        bookingRepository.save(booking);

        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    @Cacheable(value = "userPayments", key = "T(com.example.hotelbookingsystem.security.SecurityUtils).getCurrentUser().getId()")
    public List<PaymentResponse> getUserPayments() {
        List<Booking> booking = bookingRepository.findByUserId(SecurityUtils.getCurrentUser().getId());
        List<Payment> payments = new ArrayList<>();

        for (Booking book : booking) {
            paymentRepository.findByBookingId(book.getId()).ifPresent(payments::add);
        }

        return payments.stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('MANAGER')")
    @Cacheable(value = "hotelPayments", key = "#hotelId")
    public List<PaymentResponse> getHotelPayments(Long hotelId) {
        Hotel hotel = hotelRepository.findByIdAndUserId(hotelId, SecurityUtils.getCurrentUser().getId()).orElseThrow(() -> new AccessDeniedException("user dont have access for this hotel"));

        List<Booking> bookings = hotel.getRooms().stream()
                .flatMap(room -> room.getBooking().stream())
                .toList();

        List<Payment> payments = new ArrayList<>();
        for (Booking booking : bookings) {
            paymentRepository.findByBookingId(booking.getId()).ifPresent(payments::add);
        }

        return payments.stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }
}