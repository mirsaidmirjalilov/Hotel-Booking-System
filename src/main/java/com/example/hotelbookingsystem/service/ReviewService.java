package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.payload.review_related.ReviewBookingResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewCreateRequest;
import com.example.hotelbookingsystem.payload.review_related.ReviewHotelResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewHotelResponse createHotelReview(ReviewCreateRequest request);
    ReviewBookingResponse createBookingReview(ReviewCreateRequest request);
    void deleteReview(Long reviewId);  // or admin only
    ReviewBookingResponse getReviewByBookingId(Long bookingId);
    Page<ReviewHotelResponse> getHotelReviews(Long hotelId, Pageable pageable);
    List<ReviewResponse> getUserReviews();
}
