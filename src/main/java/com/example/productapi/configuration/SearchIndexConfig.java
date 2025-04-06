package com.example.productapi.configuration;

import com.example.productapi.service.ProductSearchService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchIndexConfig {

    private final ProductSearchService productSearchService;

    public SearchIndexConfig(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @PostConstruct
    public void init() {
        // Initialize indexes in a separate thread to avoid blocking application startup
        new Thread(() -> {
            try {
                Thread.sleep(5000); // Wait for Hibernate to be fully initialized
                productSearchService.indexAllProducts();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
