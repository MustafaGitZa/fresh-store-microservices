package com.fnb.UserManagementService.service;

import com.fnb.UserManagementService.model.PasswordResetToken;
import com.fnb.UserManagementService.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import com.fnb.UserManagementService.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fnb.UserManagementService.dto.RegisterRequest;
import com.fnb.UserManagementService.dto.RegisterResponse;
import com.fnb.UserManagementService.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository  passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Default to USER if no role provided
        User.Role role = request.getRole() != null ? request.getRole() : User.Role.USER;


        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        User saved = userRepository.save(user);

        return RegisterResponse.builder()
                .userId(saved.getUserId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .message("User registered successfully")
                .build();
    }

    public RegisterResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return RegisterResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("User found")
                .build();
    }

    public RegisterResponse updateUser(Long userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);

        return RegisterResponse.builder()
                .userId(saved.getUserId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .message("Profile updated successfully")
                .build();
    }

    private final PasswordResetTokenRepository resetTokenRepository;
    private final EmailService emailService;

    public Map<String, String> forgotPassword(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("No account found with that email");
        }

        String token = java.util.UUID.randomUUID().toString();

        resetTokenRepository.findByEmail(email)
                .ifPresent(resetTokenRepository::delete);

        resetTokenRepository.save(PasswordResetToken.builder()
                .token(token)
                .email(email)
                .expiresAt(LocalDateTime.now().plusHours(1))
                .used(false)
                .build());

        // Send email
        emailService.sendPasswordResetEmail(email, token);

        log.info("Password reset token for {}: {}", email, token);

        return Map.of("message", "Reset link sent to your email!");
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        User user = userRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        resetTokenRepository.save(resetToken);

        log.info("Password reset successful for: {}", resetToken.getEmail());
    }
}