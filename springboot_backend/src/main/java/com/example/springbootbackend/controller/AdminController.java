package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.OrderDTO;
import com.example.springbootbackend.dto.UserDTO;
import com.example.springbootbackend.entity.Order;
import com.example.springbootbackend.service.OrderService;
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
 * REST controller for admin-specific operations
 * Provides administrative functions for managing the system
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Administrative endpoints (Admin only)")
public class AdminController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    // PUBLIC_INTERFACE
    /**
     * Get all orders with pagination (Admin only)
     * 
     * @param pageable Pagination parameters
     * @return Page of all orders
     */
    @GetMapping("/orders")
    @Operation(summary = "Get all orders", description = "Retrieve all orders in the system (Admin only)")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        Page<OrderDTO> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Update order status (Admin only)
     * 
     * @param id Order ID
     * @param status New order status
     * @return Updated order
     */
    @PatchMapping("/orders/{id}/status")
    @Operation(summary = "Update order status", description = "Update the status of an order (Admin only)")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        OrderDTO order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get all users with pagination (Admin only)
     * 
     * @param pageable Pagination parameters
     * @return Page of all users
     */
    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Retrieve all users in the system (Admin only)")
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }
}
