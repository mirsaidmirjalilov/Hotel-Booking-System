package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends AudiTableLong {
    @Column(name = "rating",nullable = false)
    private Integer rating;

    @Column(name = "comment",nullable = false)
    private String comment;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
