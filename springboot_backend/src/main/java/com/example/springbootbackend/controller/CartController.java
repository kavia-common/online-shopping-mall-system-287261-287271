package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.AddToCartRequest;
import com.example.springbootbackend.dto.CartDTO;
import com.example.springbootbackend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for shopping cart endpoints
 * Provides cart management operations
 */
@RestController
@RequestMapping("/api/carts")
@Tag(name = "Shopping Cart", description = "Shopping cart management endpoints")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    // PUBLIC_INTERFACE
    /**
     * Get current user's cart
     * 
     * @return Cart information
     */
    @GetMapping
    @Operation(summary = "Get cart", description = "Retrieve current user's shopping cart")
    public ResponseEntity<CartDTO> getCurrentUserCart() {
        CartDTO cart = cartService.getCurrentUserCart();
        return ResponseEntity.ok(cart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Add item to cart
     * 
     * @param request Item to add
     * @return Updated cart
     */
    @PostMapping("/items")
    @Operation(summary = "Add to cart", description = "Add a product to the shopping cart")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartDTO cart = cartService.addToCart(request);
        return ResponseEntity.ok(cart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Update cart item quantity
     * 
     * @param itemId Cart item ID
     * @param quantity New quantity
     * @return Updated cart
     */
    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update cart item", description = "Update quantity of an item in the cart")
    public ResponseEntity<CartDTO> updateCartItemQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        CartDTO cart = cartService.updateCartItemQuantity(itemId, quantity);
        return ResponseEntity.ok(cart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Remove item from cart
     * 
     * @param itemId Cart item ID
     * @return Updated cart
     */
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remove from cart", description = "Remove an item from the shopping cart")
    public ResponseEntity<CartDTO> removeFromCart(@PathVariable Long itemId) {
        CartDTO cart = cartService.removeFromCart(itemId);
        return ResponseEntity.ok(cart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Clear cart
     * 
     * @return Success response
     */
    @DeleteMapping
    @Operation(summary = "Clear cart", description = "Remove all items from the shopping cart")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
