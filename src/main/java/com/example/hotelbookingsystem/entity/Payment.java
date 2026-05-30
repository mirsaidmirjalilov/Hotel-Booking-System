package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import com.example.hotelbookingsystem.enums.Currency;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends AudiTableLong {
    @Column(name = "amount",nullable = false)
    @Positive
    private BigDecimal amount;

    @Column(name = "currency",nullable = false)
    private Currency currency;

    @Column(name = "payment_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at",nullable = false)
    private LocalDateTime paidAt;
}
