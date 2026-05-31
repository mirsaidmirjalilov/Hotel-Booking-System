package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.enums.Role;
import com.example.hotelbookingsystem.mapper.UserMapper;
import com.example.hotelbookingsystem.payload.user_related.ChangePasswordRequest;
import com.example.hotelbookingsystem.payload.user_related.UserCreateRequest;
import com.example.hotelbookingsystem.payload.user_related.UserResponse;
import com.example.hotelbookingsystem.payload.user_related.UserUpdateRequest;
import com.example.hotelbookingsystem.repository.UserRepository;
import com.example.hotelbookingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreateRequest createRequest) {
        if (userRepository.findByEmail(createRequest.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .email(createRequest.email())
                .password(bCryptPasswordEncoder.encode(createRequest.password()))
                .role(createRequest.role())
                .isActive(true)
                .fullName(createRequest.fullName())
                .phoneNumber(createRequest.phoneNumber())
                .build();
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserProfile(Long id) {
        User user = userRepository.findById((id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById((id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(updateRequest.fullName());
        user.setPhoneNumber(updateRequest.phoneNumber());

        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById((id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (changePasswordRequest.oldPassword().equals(changePasswordRequest.newPassword())){
            throw new IllegalArgumentException("Passwords can not be identical");
        }

        if (!bCryptPasswordEncoder.matches(changePasswordRequest.oldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Old Password not match");
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.newPassword()));
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserRole(Long id, Role role) {
        User user = userRepository.findById((id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void enableOrDisableUser(Long userId, boolean enable) {
        User user = userRepository.findById((userId)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setIsActive(enable);
        userRepository.save(user);
    }
}
