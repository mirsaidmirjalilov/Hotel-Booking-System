package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Optional<Review> findByBookingId(Long bookingId);

    Page<Review> findByHotelId(Long hotelId, Pageable pageable);

    List<Review> findByBookingUserId(Long id);
}
