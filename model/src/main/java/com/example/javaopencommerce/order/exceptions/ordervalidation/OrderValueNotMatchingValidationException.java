package com.example.javaopencommerce.order.exceptions.ordervalidation;

import com.example.javaopencommerce.Value;

import static java.lang.String.format;

public class OrderValueNotMatchingValidationException extends OrderValidationException {

    public OrderValueNotMatchingValidationException(String type, Value actual,
                                                    Value expected) {
        super(format(
                "Issued order %s value not matching actual, real value: Issued order value: %s, Calculated value: %s",
                type, actual, expected));
    }
}
