package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.UserDTO;
import com.example.springbootbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user management endpoints
 * Provides user CRUD operations and profile management
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // PUBLIC_INTERFACE
    /**
     * Get current authenticated user profile
     * 
     * @return Current user information
     */
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Retrieve current authenticated user profile")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get user by ID
     * 
     * @param id User ID
     * @return User information
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Retrieve user information by ID (Admin only)")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get all users with pagination
     * 
     * @param pageable Pagination parameters
     * @return Page of users
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieve all users with pagination (Admin only)")
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Update user information
     * 
     * @param id User ID
     * @param userDTO Updated user data
     * @return Updated user information
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update user information")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updated = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updated);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Delete user
     * 
     * @param id User ID
     * @return Success response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete a user (Admin only)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
