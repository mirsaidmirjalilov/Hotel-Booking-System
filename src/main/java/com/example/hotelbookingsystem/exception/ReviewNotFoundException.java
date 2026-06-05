package com.example.hotelbookingsystem.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
