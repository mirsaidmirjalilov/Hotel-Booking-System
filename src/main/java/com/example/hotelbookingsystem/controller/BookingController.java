package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.booking_related.BookingCreateRequest;
import com.example.hotelbookingsystem.payload.booking_related.BookingResponse;
import com.example.hotelbookingsystem.payload.booking_related.BookingUpdateStatusRequest;
import com.example.hotelbookingsystem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BaseResponse<BookingResponse>> createBooking(@RequestBody @Valid BookingCreateRequest request){
        BookingResponse booking = bookingService.createBooking(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(booking));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<List<BookingResponse>>> getCurrentBooking(){
        List<BookingResponse> userBookings = bookingService.getUserBookings();

        return ResponseEntity.status(200).body(BaseResponse.ok(userBookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BaseResponse<BookingResponse>> getBooking(@PathVariable Long bookingId){
        BookingResponse bookingById = bookingService.getBookingById(bookingId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(bookingById));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<BaseResponse<BookingResponse>> cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok());
    }

    @GetMapping("{hotelId}/hotel/bookings")
    public ResponseEntity<BaseResponse<List<BookingResponse>>>  getBookingsByHotelId(
            @PathVariable Long hotelId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
            ){
        List<BookingResponse> hotelBookings = bookingService.getHotelBookings(hotelId, startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(hotelBookings));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BaseResponse<BookingResponse>> updateBookingStatus(@PathVariable Long bookingId, @Valid BookingUpdateStatusRequest updateStatusRequest){
        bookingService.updateBookingStatus(bookingId,updateStatusRequest.bookingStatus());

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok());
    }
}
