package com.fnb.UserManagementService.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fnb.UserManagementService.dto.RegisterRequest;
import com.fnb.UserManagementService.dto.RegisterResponse;
import com.fnb.UserManagementService.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class RegisterController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RegisterResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<RegisterResponse> updateUser(@PathVariable Long userId,
                                                       @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(userService.forgotPassword(request.get("email")));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody Map<String, String> request) {
        userService.resetPassword(request.get("token"), request.get("password"));
        return ResponseEntity.ok("Password reset successful");
    }

     @GetMapping("/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("UserManagementService is UP");
    }
}