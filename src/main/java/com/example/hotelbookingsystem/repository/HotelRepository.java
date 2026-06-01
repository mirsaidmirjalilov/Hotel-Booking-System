package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findByCityContainingIgnoreCaseAndActiveTrue(String city, Pageable pageable);
}
