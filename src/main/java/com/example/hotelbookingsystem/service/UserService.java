package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.enums.Role;
import com.example.hotelbookingsystem.payload.user_related.ChangePasswordRequest;
import com.example.hotelbookingsystem.payload.user_related.UserCreateRequest;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.example.hotelbookingsystem.payload.user_related.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest createRequest);

    UserResponse getUserProfile(Long userId);

    UserResponse updateUser(Long userId, UserUpdateRequest updateRequest);

    void changePassword(Long userId, ChangePasswordRequest changePasswordRequest);

    List<UserResponse> getUsers();

    void changeUserRole(Long userId, Role role);

    void enableOrDisableUser(Long userId, boolean enable);
}
