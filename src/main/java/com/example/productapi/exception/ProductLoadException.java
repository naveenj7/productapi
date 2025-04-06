package com.example.productapi.exception;


public class ProductLoadException extends RuntimeException {
    public ProductLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
