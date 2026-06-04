package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.Payment;
import com.example.hotelbookingsystem.payload.payment_related.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentStatus(),
                payment.getPaidAt().toLocalDate()
        );
    }
}
