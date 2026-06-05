package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelCreateRequest;
import com.example.hotelbookingsystem.payload.hotel_related.HotelResponse;
import com.example.hotelbookingsystem.payload.hotel_related.HotelUpdateRequest;
import com.example.hotelbookingsystem.payload.room_related.RoomResponse;
import com.example.hotelbookingsystem.service.HotelService;
import com.example.hotelbookingsystem.service.RoomService;
import jakarta.validation.Valid;
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
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<BaseResponse<HotelResponse>> createHotel(@RequestBody @Valid HotelCreateRequest hotelCreateRequest) {
        HotelResponse hotel = hotelService.createHotel(hotelCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(hotel));
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<BaseResponse<HotelResponse>> updateHotel(@PathVariable Long hotelId,@RequestBody @Valid HotelUpdateRequest hotelRequest) {
        HotelResponse hotelResponse = hotelService.updateHotel(hotelId,hotelRequest);

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

    @DeleteMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<HotelResponse>>> getHotelsByCity(
            @RequestParam(name = "city") String city,
            @PageableDefault Pageable pageable
    ) {
        Page<HotelResponse> hotelResponses = hotelService.searchHotels(city, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(hotelResponses));
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<BaseResponse<List<RoomResponse>>> getRoomsByHotel(
            @PathVariable Long hotelId
    ) {
        List<RoomResponse> roomsByHotel = roomService.getRoomsByHotel(hotelId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(roomsByHotel));
    }
}
