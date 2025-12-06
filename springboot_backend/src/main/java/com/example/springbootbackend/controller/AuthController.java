package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.AuthResponse;
import com.example.springbootbackend.dto.LoginRequest;
import com.example.springbootbackend.dto.RegisterRequest;
import com.example.springbootbackend.dto.UserDTO;
import com.example.springbootbackend.entity.User;
import com.example.springbootbackend.mapper.EntityMapper;
import com.example.springbootbackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints
 * Provides login and registration functionality
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication and registration endpoints")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private EntityMapper mapper;
    
    // PUBLIC_INTERFACE
    /**
     * Authenticates user and returns JWT token
     * 
     * @param request Login credentials
     * @return Authentication response with JWT token
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and get JWT token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Registers a new user
     * 
     * @param request Registration details
     * @return Created user information
     */
    @PostMapping("/register")
    @Operation(summary = "Register", description = "Register a new user account")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(mapper.toUserDTO(user));
    }
}
