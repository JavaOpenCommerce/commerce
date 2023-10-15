package com.example.opencommerce.app.pricing.exceptions;

import com.example.opencommerce.app.BaseAppException;

import java.util.List;

public class PriceOperationException extends BaseAppException {

    public PriceOperationException(List<String> errors) {
        super(String.join(", ", errors));
    }
}
