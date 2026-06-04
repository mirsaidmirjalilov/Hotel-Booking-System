package com.example.hotelbookingsystem.payload.payment_related;

import com.example.hotelbookingsystem.enums.Currency;
import com.example.hotelbookingsystem.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentResponse(
    Long paymentId,
    Long bookingId,
    BigDecimal totalPrice,
    Currency currency,
    PaymentStatus paymentStatus,
    LocalDate paidAt
) {
}
