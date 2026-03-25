package com.fnb.UserManagementService.dto;

import com.fnb.UserManagementService.model.User;
import lombok.Data;

@Data
public class RegisterRequest {
    
    private String username;
    private String email;
    private String password;
    private User.Role role;
}
