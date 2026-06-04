package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Review;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.exception.BookingNotFoundException;
import com.example.hotelbookingsystem.exception.HotelNotFoundException;
import com.example.hotelbookingsystem.mapper.ReviewMapper;
import com.example.hotelbookingsystem.payload.review_related.ReviewBookingResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewCreateRequest;
import com.example.hotelbookingsystem.payload.review_related.ReviewHotelResponse;
import com.example.hotelbookingsystem.payload.review_related.ReviewResponse;
import com.example.hotelbookingsystem.repository.BookingRepository;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.ReviewRepository;
import com.example.hotelbookingsystem.security.SecurityUtils;
import com.example.hotelbookingsystem.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    @CacheEvict(value = {"hotelReviews", "userReviews"}, allEntries = true)
    public ReviewHotelResponse createHotelReview(ReviewCreateRequest request) {
        Review review = Review.builder()
                .hotel(hotelRepository.findById(request.id()).orElseThrow(() -> new HotelNotFoundException("hotel not found")))
                .comment(request.comment())
                .rating(request.rating())
                .createdAt(LocalDateTime.now())
                .booking(bookingRepository.findById(2L).orElseThrow(() -> new BookingNotFoundException("booking not found")))
                .build();
        reviewRepository.save(review);

        return reviewMapper.toReviewHotelResponse(review);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"hotelReviews", "userReviews"}, allEntries = true)
    public ReviewBookingResponse createBookingReview(ReviewCreateRequest request) {
        Review review = Review.builder()
                .booking(bookingRepository.findById(request.id()).orElseThrow(() -> new BookingNotFoundException("hotel not found")))
                .comment(request.comment())
                .rating(request.rating())
                .createdAt(LocalDateTime.now())
                .hotel(hotelRepository.findById(11L).orElseThrow(() -> new HotelNotFoundException("hotel not found")))
                .build();
        reviewRepository.save(review);

        return reviewMapper.toReviewBookingResponse(review);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    @CacheEvict(value = {"hotelReviews", "userReviews"}, allEntries = true)
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new HotelNotFoundException("hotel not found"));

        reviewRepository.delete(review);
    }

    @Override
    @Cacheable(value = "reviewByBooking", key = "#bookingId")
    public ReviewBookingResponse getReviewByBookingId(Long bookingId) {
        Review review = reviewRepository.findByBookingId(bookingId).orElseThrow(() -> new HotelNotFoundException("hotel not found"));

        return reviewMapper.toReviewBookingResponse(review);
    }

    @Override
    @Cacheable(value = "hotelReviews", key = "#hotelId + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ReviewHotelResponse> getHotelReviews(Long hotelId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByHotelId(hotelId, pageable);

        return reviews.map(reviewMapper::toReviewHotelResponse);
    }

    @Override
    @Cacheable(value = "userReviews", key = "#user.getId()")
    public List<ReviewResponse> getUserReviews() {
        User user = SecurityUtils.getCurrentUser();

        List<Review> reviews = reviewRepository.findByBookingUserId(user.getId());

        return reviews.stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }
}