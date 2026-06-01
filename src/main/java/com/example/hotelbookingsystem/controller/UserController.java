package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.enums.Role;
import com.example.hotelbookingsystem.payload.BaseResponse;
import com.example.hotelbookingsystem.payload.user_related.ChangePasswordRequest;
import com.example.hotelbookingsystem.payload.user_related.UserCreateRequest;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.example.hotelbookingsystem.payload.user_related.UserUpdateRequest;
import com.example.hotelbookingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(userService.createUser(userCreateRequest)));
    }

    @PutMapping("/{userId}/role/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserRole(@PathVariable Long userId, Role role) {
        userService.changeUserRole(userId, role);
    }

    @PutMapping("/{userId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserStatus(@PathVariable Long userId, boolean active) {
        userService.enableOrDisableUser(userId, active);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<BaseResponse<UserResponse>> getUserProfile(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok(userService.getUserProfile(userId)));
    }

    @PutMapping("/{userId}/profile/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfile(@PathVariable long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
    }

    @PutMapping("/{userId}/password/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserPassword(@PathVariable long userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(userId, changePasswordRequest);
    }
}
