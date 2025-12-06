package com.example.springbootbackend.service;

import com.example.springbootbackend.dto.ProductDTO;
import com.example.springbootbackend.entity.Product;
import com.example.springbootbackend.mapper.EntityMapper;
import com.example.springbootbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for product management operations
 */
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    // PUBLIC_INTERFACE
    /**
     * Retrieves all products with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(mapper::toProductDTO);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Retrieves available products with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAvailableProducts(Pageable pageable) {
        return productRepository.findByAvailableTrue(pageable)
                .map(mapper::toProductDTO);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Retrieves products by category with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable)
                .map(mapper::toProductDTO);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Searches products by name with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(mapper::toProductDTO);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Retrieves product by ID
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapper.toProductDTO(product);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Creates a new product
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return mapper.toProductDTO(savedProduct);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Updates an existing product
     */
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(productDTO.getCategory());
        product.setImageUrl(productDTO.getImageUrl());
        product.setAvailable(productDTO.getAvailable());
        product.setSku(productDTO.getSku());
        
        Product updatedProduct = productRepository.save(product);
        return mapper.toProductDTO(updatedProduct);
    }
    
    // PUBLIC_INTERFACE
    /**
     * Deletes a product
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
