package com.fnb.UserManagementService.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fnb.UserManagementService.dto.RegisterRequest;
import com.fnb.UserManagementService.dto.RegisterResponse;
import com.fnb.UserManagementService.service.UserService;

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

     @GetMapping("/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("UserManagementService is UP");
    }
}