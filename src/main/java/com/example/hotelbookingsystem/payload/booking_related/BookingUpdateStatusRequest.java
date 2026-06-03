package com.example.hotelbookingsystem.payload.booking_related;

import com.example.hotelbookingsystem.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingUpdateStatusRequest(
        BookingStatus bookingStatus
) {
}
