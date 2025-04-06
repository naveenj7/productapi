package com.example.productapi.configuration;

import com.example.productapi.model.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "productClient", url = "https://dummyjson.com", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {
    @GetMapping("/products")
    ProductResponse getProducts();
}
