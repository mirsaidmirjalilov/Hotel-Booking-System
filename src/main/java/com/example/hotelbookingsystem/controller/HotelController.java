package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelRequest;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelUpdateRequest;
import com.example.hotelbookingsystem.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/{managerId}/create")
    public ResponseEntity<BaseResponse<HotelResponse>> createHotel(@PathVariable Long managerId, @RequestBody HotelRequest hotelRequest) {
        HotelResponse hotel = hotelService.createHotel(managerId, hotelRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(hotel));
    }

    @PutMapping("/{hotelId}/{managerId}/update")
    public ResponseEntity<BaseResponse<HotelResponse>> updateHotel(@PathVariable Long hotelId, @PathVariable Long managerId, @RequestBody HotelUpdateRequest hotelRequest) {
        HotelResponse hotelResponse = hotelService.updateHotel(hotelId, managerId, hotelRequest);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(hotelResponse));
    }

    @GetMapping("/{hotelId}/hotel")
    public ResponseEntity<BaseResponse<HotelResponse>> getHotel(@PathVariable Long hotelId) {
        HotelResponse hotelById = hotelService.getHotelById(hotelId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(hotelById));
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<BaseResponse<List<HotelResponse>>> getAllHotels(@PathVariable Long managerId) {
        List<HotelResponse> myHotels = hotelService.getMyHotels(managerId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(myHotels));
    }

    @DeleteMapping("/{hotelId}/{managerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long hotelId, @PathVariable Long managerId) {
        hotelService.deleteHotel(hotelId, managerId);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<HotelResponse>>> getHotelsByCity(
            @RequestParam(name = "city") String city,
            @PageableDefault Pageable pageable
    ) {
        Page<HotelResponse> hotelResponses = hotelService.searchHotels(city, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(hotelResponses));
    }
}
