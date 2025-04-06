package com.example.productapi.controller;

import com.example.productapi.entity.Product;
import com.example.productapi.service.ProductSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products/search")
@Tag(name = "Product Search", description = "Full-text search endpoints for products")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping
    @Operation(summary = "Search products by title or description")
    public List<Product> search(
            @Parameter(description = "Search term to look for in title or description")
            @RequestParam String q) {
        return productSearchService.search(q);
    }

    @GetMapping("/advanced")
    @Operation(summary = "Advanced search with pagination and sorting")
    public List<Product> advancedSearch(
            @Parameter(description = "Search term")
            @RequestParam String q,
            @Parameter(description = "Maximum number of results")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Offset for pagination")
            @RequestParam(defaultValue = "0") int offset) {
        return productSearchService.advancedSearch(q, limit, offset);
    }

    @PostMapping("/reindex")
    @Operation(summary = "Reindex all products", description = "Triggers a rebuild of the search index")
    public String reindex() {
        productSearchService.indexAllProducts();
        return "Reindexing started in background";
    }
}