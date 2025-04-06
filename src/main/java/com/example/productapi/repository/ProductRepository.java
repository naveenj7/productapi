package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Product> searchProducts(String searchText);

    Optional<Product> findBySku(String sku);
}
