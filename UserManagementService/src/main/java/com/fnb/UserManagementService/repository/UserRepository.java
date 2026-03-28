package com.fnb.UserManagementService.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fnb.UserManagementService.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
} 
