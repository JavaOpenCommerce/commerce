package com.example.opencommerce.domain.order.exceptions.ordervalidation;


import com.example.opencommerce.domain.order.exceptions.ExceptionWithPayload;

public class OutOfStockItemsValidationException extends OrderValidationException implements
        ExceptionWithPayload {

    private final String outOfStockItems;

    public OutOfStockItemsValidationException(String outOfStockItems) {
        super(String.format("Not enough items in a stock! Items exceeding stocks - ids: %s", outOfStockItems));
        this.outOfStockItems = outOfStockItems;
    }

    @Override
    public String getPayload() {
        return outOfStockItems;
    }
}
