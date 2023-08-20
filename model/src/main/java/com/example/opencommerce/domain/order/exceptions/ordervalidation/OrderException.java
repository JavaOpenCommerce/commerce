package com.example.opencommerce.domain.order.exceptions.ordervalidation;

public class OrderException extends RuntimeException {

    public OrderException() {
    }

    OrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderException(String message) {
        super(message);
    }
}
