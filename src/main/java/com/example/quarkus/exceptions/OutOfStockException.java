package com.example.quarkus.exceptions;

import static com.example.utils.converters.JsonConverter.convertToJson;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(ItemExceptionEntity itemExceptionEntity) {
        super(convertToJson(itemExceptionEntity));
    }
}
