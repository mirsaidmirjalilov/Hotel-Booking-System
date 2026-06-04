package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.payment_related.PaymentMockRequest;
import com.example.hotelbookingsystem.payload.payment_related.PaymentResponse;
import com.example.hotelbookingsystem.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<BaseResponse<PaymentResponse>> createPayment(@RequestBody @Valid PaymentMockRequest paymentMockRequest){
        PaymentResponse payment = paymentService.createPayment(paymentMockRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(payment));
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponse<List<PaymentResponse>>> getAllUserPayments(){
        List<PaymentResponse> userPayments = paymentService.getUserPayments();

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(userPayments));
    }

    @GetMapping("/{hotelId}/hotel")
    public ResponseEntity<BaseResponse<List<PaymentResponse>>> getAllHotelPayments(@PathVariable Long hotelId){
        List<PaymentResponse> hotelPayments = paymentService.getHotelPayments(hotelId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(hotelPayments));
    }

    @DeleteMapping("/{paymentId}/cancel")
    public ResponseEntity<BaseResponse<PaymentResponse>> cancelPayment(@PathVariable Long paymentId){
        PaymentResponse paymentResponse = paymentService.cancelPayment(paymentId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(paymentResponse));
    }
}
