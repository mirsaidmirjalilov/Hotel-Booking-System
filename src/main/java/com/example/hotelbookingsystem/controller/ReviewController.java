package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewBookingResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewCreateRequest;
import com.example.hotelbookingsystem.payload.review_related.ReviewHotelResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewResponse;
import com.example.hotelbookingsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/hotel")
    public ResponseEntity<BaseResponse<ReviewHotelResponse>> createHotelReview(@RequestBody ReviewCreateRequest request) {
        ReviewHotelResponse hotelReview = reviewService.createHotelReview(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(hotelReview));
    }

    @PostMapping("/booking")
    public ResponseEntity<BaseResponse<ReviewBookingResponse>> createBookingReview(@RequestBody ReviewCreateRequest request) {
        ReviewBookingResponse bookingReview = reviewService.createBookingReview(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(bookingReview));
    }

    @GetMapping("/{bookingId}/booking")
    public ResponseEntity<BaseResponse<ReviewBookingResponse>> getBookingReview(@PathVariable Long bookingId) {
        ReviewBookingResponse reviewByBookingId = reviewService.getReviewByBookingId(bookingId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(reviewByBookingId));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<BaseResponse<Page<ReviewHotelResponse>>> getHotelReview(
            @PathVariable Long hotelId,
            @PageableDefault Pageable pageable
    ) {
        Page<ReviewHotelResponse> hotelReviews = reviewService.getHotelReviews(hotelId, pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(hotelReviews));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getCurrentUser() {
        List<ReviewResponse> userReviews = reviewService.getUserReviews();

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(userReviews));
    }

    @DeleteMapping("/{reviewId}/review")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
