package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.ProductDTO;
import com.example.springbootbackend.service.ProductService;
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
 * REST controller for product management endpoints
 * Provides product CRUD operations and search functionality
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // PUBLIC_INTERFACE
    /**
     * Get all products with pagination
     * 
     * @param pageable Pagination parameters
     * @return Page of products
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all products with pagination")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get available products with pagination
     * 
     * @param pageable Pagination parameters
     * @return Page of available products
     */
    @GetMapping("/available")
    @Operation(summary = "Get available products", description = "Retrieve only available products with pagination")
    public ResponseEntity<Page<ProductDTO>> getAvailableProducts(Pageable pageable) {
        Page<ProductDTO> products = productService.getAvailableProducts(pageable);
        return ResponseEntity.ok(products);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get product by ID
     * 
     * @param id Product ID
     * @return Product information
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve product information by ID")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Search products by name
     * 
     * @param name Search term
     * @param pageable Pagination parameters
     * @return Page of matching products
     */
    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name")
    public ResponseEntity<Page<ProductDTO>> searchProducts(@RequestParam String name, Pageable pageable) {
        Page<ProductDTO> products = productService.searchProducts(name, pageable);
        return ResponseEntity.ok(products);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Get products by category
     * 
     * @param category Product category
     * @param pageable Pagination parameters
     * @return Page of products in category
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieve products filtered by category")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(@PathVariable String category, Pageable pageable) {
        Page<ProductDTO> products = productService.getProductsByCategory(category, pageable);
        return ResponseEntity.ok(products);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Create a new product (Admin only)
     * 
     * @param productDTO Product data
     * @return Created product
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product", description = "Create a new product (Admin only)")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO created = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Update an existing product (Admin only)
     * 
     * @param id Product ID
     * @param productDTO Updated product data
     * @return Updated product
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product", description = "Update an existing product (Admin only)")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updated = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updated);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Delete a product (Admin only)
     * 
     * @param id Product ID
     * @return Success response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product", description = "Delete a product (Admin only)")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
