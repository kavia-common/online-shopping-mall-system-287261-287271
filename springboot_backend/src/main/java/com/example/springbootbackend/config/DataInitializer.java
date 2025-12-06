package com.example.springbootbackend.config;

import com.example.springbootbackend.entity.Product;
import com.example.springbootbackend.entity.User;
import com.example.springbootbackend.repository.ProductRepository;
import com.example.springbootbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Component to initialize sample data on application startup
 * This is useful for development and testing purposes
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (userRepository.count() > 0) {
            return; // Data already initialized
        }
        
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@shoppingmall.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("System Administrator");
        admin.setPhoneNumber("1234567890");
        admin.setAddress("123 Admin Street, City");
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("ADMIN");
        adminRoles.add("USER");
        admin.setRoles(adminRoles);
        admin.setEnabled(true);
        userRepository.save(admin);
        
        // Create regular user
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setFullName("John Doe");
        user.setPhoneNumber("9876543210");
        user.setAddress("456 User Avenue, Town");
        Set<String> userRoles = new HashSet<>();
        userRoles.add("USER");
        user.setRoles(userRoles);
        user.setEnabled(true);
        userRepository.save(user);
        
        // Create sample products
        createProduct("Laptop", "High-performance laptop with 16GB RAM", new BigDecimal("999.99"), 10, "Electronics");
        createProduct("Smartphone", "Latest model smartphone with 5G", new BigDecimal("699.99"), 25, "Electronics");
        createProduct("Headphones", "Wireless noise-canceling headphones", new BigDecimal("199.99"), 50, "Electronics");
        createProduct("Coffee Maker", "Programmable coffee maker", new BigDecimal("79.99"), 15, "Home & Kitchen");
        createProduct("Blender", "High-speed blender for smoothies", new BigDecimal("59.99"), 20, "Home & Kitchen");
        createProduct("Running Shoes", "Comfortable running shoes", new BigDecimal("89.99"), 30, "Sports & Outdoors");
        createProduct("Yoga Mat", "Non-slip yoga mat", new BigDecimal("29.99"), 40, "Sports & Outdoors");
        createProduct("Backpack", "Durable travel backpack", new BigDecimal("49.99"), 35, "Bags & Luggage");
        createProduct("Watch", "Elegant wrist watch", new BigDecimal("149.99"), 20, "Accessories");
        createProduct("Sunglasses", "UV protection sunglasses", new BigDecimal("39.99"), 45, "Accessories");
        
        System.out.println("Sample data initialized successfully!");
    }
    
    private void createProduct(String name, String description, BigDecimal price, int stock, String category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stock);
        product.setCategory(category);
        product.setAvailable(true);
        product.setSku("SKU-" + name.toUpperCase().replace(" ", "-"));
        productRepository.save(product);
    }
}
