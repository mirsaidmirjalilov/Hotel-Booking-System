package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.entity.Booking;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndUserId(@NotNull Long aLong, Long id);

    List<Booking> findByUserId(Long id);
}
