package com.example.springbootbackend.service;

import com.example.springbootbackend.dto.AddToCartRequest;
import com.example.springbootbackend.dto.CartDTO;
import com.example.springbootbackend.entity.Cart;
import com.example.springbootbackend.entity.CartItem;
import com.example.springbootbackend.entity.Product;
import com.example.springbootbackend.entity.User;
import com.example.springbootbackend.mapper.EntityMapper;
import com.example.springbootbackend.repository.CartRepository;
import com.example.springbootbackend.repository.ProductRepository;
import com.example.springbootbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for shopping cart operations
 */
@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    // PUBLIC_INTERFACE
    /**
     * Retrieves current user's cart
     */
    @Transactional(readOnly = true)
    public CartDTO getCurrentUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
        
        return mapper.toCartDTO(cart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Adds item to current user's cart
     */
    @Transactional
    public CartDTO addToCart(AddToCartRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
        
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));
        
        if (!product.getAvailable()) {
            throw new RuntimeException("Product is not available");
        }
        
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock available");
        }
        
        // Check if product already in cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            cart.getItems().add(newItem);
        }
        
        Cart savedCart = cartRepository.save(cart);
        return mapper.toCartDTO(savedCart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Updates cart item quantity
     */
    @Transactional
    public CartDTO updateCartItemQuantity(Long itemId, Integer quantity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            cart.getItems().remove(cartItem);
        } else {
            if (cartItem.getProduct().getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock available");
            }
            cartItem.setQuantity(quantity);
        }
        
        Cart savedCart = cartRepository.save(cart);
        return mapper.toCartDTO(savedCart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Removes item from cart
     */
    @Transactional
    public CartDTO removeFromCart(Long itemId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        
        Cart savedCart = cartRepository.save(cart);
        return mapper.toCartDTO(savedCart);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Clears all items from current user's cart
     */
    @Transactional
    public void clearCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
