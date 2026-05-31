package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.mapper.UserMapper;
import com.example.hotelbookingsystem.payload.auth_related.AuthResponse;
import com.example.hotelbookingsystem.payload.auth_related.LoginRequest;
import com.example.hotelbookingsystem.payload.auth_related.RegisterRequest;
import com.example.hotelbookingsystem.repository.UserRepository;
import com.example.hotelbookingsystem.security.JwtTokenUtil;
import com.example.hotelbookingsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authenticationToken);
        String token = jwtTokenUtil.generateToken(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new AuthResponse("logged in", token, userMapper.mapToUserResponse(user));
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .role(registerRequest.role())
                .password(passwordEncoder.encode(registerRequest.password()))
                .fullName(registerRequest.fullName())
                .email(registerRequest.email())
                .phoneNumber(registerRequest.phoneNumber())
                .isActive(true)
                .build();
        userRepository.save(user);

        return new AuthResponse("registered", userMapper.mapToUserResponse(user));
    }
}
