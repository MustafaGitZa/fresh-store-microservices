package com.fnb.UserManagementService.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

     @GetMapping("/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("UserManagementService is UP");
    }
}