package com.example.opencommerce.app.pricing;

import java.util.List;

public class PriceOperationException extends RuntimeException {

    public PriceOperationException(List<String> errors) {
        super(String.join(", ", errors));
    }
}
