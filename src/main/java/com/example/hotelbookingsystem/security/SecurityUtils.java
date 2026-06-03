package com.example.hotelbookingsystem.security;

import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.security.userdetails.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityUtils {
     public static User getCurrentUser() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getPrincipal();

        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.user();
        }
        else if (principal instanceof User user) {
            return user;
        }
        else if (principal instanceof String) {
           throw new IllegalStateException("User not properly authenticated");
        }

        throw new IllegalStateException("User not found in security context");
    }
}
