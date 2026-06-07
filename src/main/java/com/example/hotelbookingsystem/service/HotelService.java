package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.payload.hotel_related.HotelCreateRequest;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    HotelResponse createHotel(HotelCreateRequest request);
    HotelResponse updateHotel(Long hotelId, HotelUpdateRequest request);
    HotelResponse getHotelById(Long hotelId);
    Page<HotelResponse> searchHotels(String city, Pageable pageable);
    List<HotelResponse> getMyHotels(Long managerId);
    void deleteHotel(Long hotelId);
    Page<HotelResponse> getAllHotels(Pageable pageable);
}
