package com.example.opencommerce.app.catalog.exceptions;

public class CategoryException extends RuntimeException {

    public CategoryException() {
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
