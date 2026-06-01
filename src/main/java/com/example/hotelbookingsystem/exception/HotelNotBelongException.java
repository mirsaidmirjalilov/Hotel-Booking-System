package com.example.hotelbookingsystem.exception;

public class HotelNotBelongException extends RuntimeException {
    public HotelNotBelongException(String message) {
        super(message);
    }
}
