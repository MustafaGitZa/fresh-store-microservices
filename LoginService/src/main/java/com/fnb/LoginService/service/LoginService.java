package com.fnb.LoginService.service;

import com.fnb.LoginService.dto.AuthResponse;
import com.fnb.LoginService.dto.LoginRequest;
import com.fnb.LoginService.model.User;
import com.fnb.LoginService.repository.UserRepository;
import com.fnb.LoginService.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getUserId(),
                user.getRole().name(),
                user.getEmail()
        );

        return AuthResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .email(user.getEmail())
                .message("Login successful")
                .build();
    }
}