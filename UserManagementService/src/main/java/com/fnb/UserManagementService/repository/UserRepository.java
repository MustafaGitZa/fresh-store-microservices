package com.fnb.UserManagementService.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fnb.UserManagementService.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 
