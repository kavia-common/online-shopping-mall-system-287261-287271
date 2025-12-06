package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.CreateOrderRequest;
import com.example.springbootbackend.dto.OrderDTO;
import com.example.springbootbackend.entity.Order;
import com.example.springbootbackend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for order management endpoints
 * Provides order CRUD operations and order processing
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // PUBLIC_INTERFACE
    /**
     * Get all orders for current user with pagination
     * 
     * @param pageable Pagination parameters
     * @return Page of orders
     */
    @GetMapping
    @Operation(summary = "Get user orders", description = "Retrieve orders for current user with pagination")
    public ResponseEntity<Page<OrderDTO>> getCurrentUserOrders(Pageable pageable) {
        Page<OrderDTO> orders = orderService.getCurrentUserOrders(pageable);
        return ResponseEntity.ok(orders);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get order by ID
     * 
     * @param id Order ID
     * @return Order information
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve order information by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Create new order from cart
     * 
     * @param request Order creation details
     * @return Created order
     */
    @PostMapping
    @Operation(summary = "Create order", description = "Create a new order from current user's cart")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDTO order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Cancel an order
     * 
     * @param id Order ID
     * @return Cancelled order
     */
    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel order", description = "Cancel an existing order")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long id) {
        OrderDTO order = orderService.cancelOrder(id);
        return ResponseEntity.ok(order);
    }
}
