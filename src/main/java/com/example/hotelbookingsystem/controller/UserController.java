package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.enums.Role;
import com.example.hotelbookingsystem.payload.user_related.ChangePasswordRequest;
import com.example.hotelbookingsystem.payload.user_related.UserCreateRequest;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.example.hotelbookingsystem.payload.user_related.UserUpdateRequest;
import com.example.hotelbookingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userCreateRequest));
    }

    @PutMapping("/{userId}/role/update")
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserRole(@PathVariable Long userId, Role role) {
        userService.changeUserRole(userId, role);
    }

    @PutMapping("/{userId}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserStatus(@PathVariable Long userId, boolean active) {
        userService.enableOrDisableUser(userId, active);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(userId));
    }

    @PutMapping("/{userId}/profile/update")
    public void updateUserProfile(@PathVariable long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
    }

    @PutMapping("/{userId}/password/update")
    public void updateUserPassword(@PathVariable long userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(userId, changePasswordRequest);
    }
}
