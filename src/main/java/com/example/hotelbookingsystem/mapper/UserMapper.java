package com.example.hotelbookingsystem.mapper;

import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );
    }
}
