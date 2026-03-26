package com.fnb.UserManagementService.config;

import com.fnb.UserManagementService.model.User;
import com.fnb.UserManagementService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdmin();
    }

    private void seedAdmin() {
        if (userRepository.existsByUsername("admin")) {
            log.info("Admin user already exists — skipping seed");
            return;
        }

        User admin = User.builder()
                .username("admin")
                .email("admin@freshstore.com")
                .password(passwordEncoder.encode("admin123"))
                .role(User.Role.ADMIN)
                .build();

        userRepository.save(admin);
        log.info("Admin user created successfully");
        log.info("Username: admin | Password: admin123 | Role: ADMIN");
    }
}
