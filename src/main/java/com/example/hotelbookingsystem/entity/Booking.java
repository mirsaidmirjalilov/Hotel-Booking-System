package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import com.example.hotelbookingsystem.enums.BookingStatus;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends AudiTableLong {
    @Column(name = "check_in",nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out",nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "total_guests",nullable = false)
    @Positive
    private Integer totalGuests;

    @Column(name = "total_price",nullable = false)
    @Positive
    private BigDecimal totalPrice;

    @Column(name = "booking_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "payment_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "booked_at",nullable = false)
    private LocalDateTime bookedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Review review;
}
