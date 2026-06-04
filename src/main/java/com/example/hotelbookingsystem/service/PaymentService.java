package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.payload.payment_related.PaymentMockRequest;
import com.example.hotelbookingsystem.payload.payment_related.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentMockRequest paymentMockRequest);

    PaymentResponse cancelPayment(Long paymentId);

    List<PaymentResponse> getUserPayments();

    List<PaymentResponse> getHotelPayments(Long hotelId);
}
