package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.Review;
import com.example.hotelbookingsystem.payload.review_related.ReviewBookingResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewHotelResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewResponse;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewHotelResponse toReviewHotelResponse(Review review) {
        return new ReviewHotelResponse(
                review.getId(),
                review.getHotel().getId(),
                review.getHotel().getHotelName(),
                review.getRating(),
                review.getComment()
        );
    }

    public ReviewBookingResponse toReviewBookingResponse(Review review) {
        return new ReviewBookingResponse(
                review.getId(),
                review.getBooking().getId(),
                review.getBooking().getRoom().getRoomType(),
                review.getRating(),
                review.getComment()
        );
    }

    public ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getBooking().getId(),
                review.getHotel().getId(),
                review.getHotel().getHotelName(),
                review.getBooking().getRoom().getRoomType(),
                review.getRating(),
                review.getComment()
        );
    }
}
