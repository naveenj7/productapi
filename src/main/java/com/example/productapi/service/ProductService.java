package com.example.productapi.service;


import com.example.productapi.configuration.ProductFeignClient;
import com.example.productapi.entity.Product;
import com.example.productapi.exception.ProductLoadException;
import com.example.productapi.model.ProductResponse;
import com.example.productapi.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductFeignClient productClient;

    private static final String PRODUCT_SERVICE = "productService";

    @Transactional
    @CircuitBreaker(name = PRODUCT_SERVICE, fallbackMethod = "loadProductsFallback")
    public void loadProducts() {
        try {
            ProductResponse response = productClient.getProducts();
            if (response != null && response.getProducts() != null) {
                List<Product> products = response.getProducts().stream()
                        .map(p -> Product.builder()
                                .id(p.getId())
                                .title(p.getTitle())
                                .description(p.getDescription())
                                .sku(p.getSku())
                                .build())
                        .collect(Collectors.toList());
                productRepository.saveAll(products);
            }
        } catch (Exception e) {
            log.error("Exception while loading products", e);
            throw new ProductLoadException("Exception while loading products: " + e.getMessage(), e);
        }
    }

    public void loadProductsFallback(Throwable t) {
        log.warn("Fallback triggered for loadProducts: {}", t.toString());
        throw new ProductLoadException("Fallback: Failed to load products due to: " + t.getMessage(), t);
    }

    public List<Product> searchProducts(String searchText) {
           return productRepository.searchProducts(searchText);

    }

public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
}

public Optional<Product> getProductBySku(String sku) {
    return productRepository.findBySku(sku);
}
}
