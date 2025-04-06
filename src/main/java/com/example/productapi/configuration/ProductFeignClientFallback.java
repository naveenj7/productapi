package com.example.productapi.configuration;

import com.example.productapi.model.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public ProductResponse getProducts() {
        ProductResponse fallbackResponse = new ProductResponse();
        fallbackResponse.setProducts(List.of());
        return fallbackResponse;
    }
}
