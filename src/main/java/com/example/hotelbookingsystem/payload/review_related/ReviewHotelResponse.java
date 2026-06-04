package com.example.hotelbookingsystem.payload.review_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReviewHotelResponse(
        Long reviewId,
        Long hotelId,
        String hotelName,
        Integer rating,
        String comment
) {
}
