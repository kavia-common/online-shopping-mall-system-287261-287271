package com.example.springbootbackend.repository;

import com.example.springbootbackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity database operations
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findByAvailableTrue(Pageable pageable);
    
    Page<Product> findByCategory(String category, Pageable pageable);
    
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
