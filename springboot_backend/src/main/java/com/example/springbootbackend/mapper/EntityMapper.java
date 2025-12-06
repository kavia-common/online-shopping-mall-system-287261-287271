package com.example.springbootbackend.mapper;

import com.example.springbootbackend.dto.*;
import com.example.springbootbackend.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Utility component for mapping entities to DTOs and vice versa
 */
@Component
public class EntityMapper {
    
    // PUBLIC_INTERFACE
    /**
     * Maps User entity to UserDTO
     */
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRoles(user.getRoles());
        dto.setEnabled(user.getEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        
        return dto;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Maps Product entity to ProductDTO
     */
    public ProductDTO toProductDTO(Product product) {
        if (product == null) return null;
        
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        dto.setAvailable(product.getAvailable());
        dto.setSku(product.getSku());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        
        return dto;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Maps ProductDTO to Product entity
     */
    public Product toProduct(ProductDTO dto) {
        if (dto == null) return null;
        
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        product.setSku(dto.getSku());
        
        return product;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Maps Order entity to OrderDTO
     */
    public OrderDTO toOrderDTO(Order order) {
        if (order == null) return null;
        
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setUsername(order.getUser().getUsername());
        dto.setItems(order.getItems().stream()
                .map(this::toOrderItemDTO)
                .collect(Collectors.toList()));
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setPhoneNumber(order.getPhoneNumber());
        dto.setNotes(order.getNotes());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        
        return dto;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Maps OrderItem entity to OrderItemDTO
     */
    public OrderItemDTO toOrderItemDTO(OrderItem item) {
        if (item == null) return null;
        
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getSubtotal());
        
        return dto;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Maps Cart entity to CartDTO
     */
    public CartDTO toCartDTO(Cart cart) {
        if (cart == null) return null;
        
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setItems(cart.getItems().stream()
                .map(this::toCartItemDTO)
                .collect(Collectors.toList()));
        
        // Calculate total amount
        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalAmount(totalAmount);
        
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        
        return dto;
    }
    
    // PUBLIC_INTERFACE
    /**
     * Maps CartItem entity to CartItemDTO
     */
    public CartItemDTO toCartItemDTO(CartItem item) {
        if (item == null) return null;
        
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        
        return dto;
    }
}
