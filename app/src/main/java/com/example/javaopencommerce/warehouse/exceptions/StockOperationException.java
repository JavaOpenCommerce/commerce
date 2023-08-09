package com.example.javaopencommerce.warehouse.exceptions;

import java.util.List;

import static java.lang.String.format;

public class StockOperationException extends RuntimeException {

    private static final String STOCK_ADDITION_FAILURE_MSG = "Failure when trying to increase stock, errors: %s";
    private static final String STOCK_RESERVATION_FAILURE_MSG = "Failure when trying to reserve stock, errors: %s";
    private static final String EXECUTE_RESERVATION_FAILURE_MSG = "Failure when trying to execute stock reservation, errors: %s";

    private StockOperationException(String message) {
        super(message);
    }

    public static StockOperationException addStockFailed(List<String> errors) {
        return new StockOperationException(format(STOCK_ADDITION_FAILURE_MSG, String.join(", ", errors)));
    }

    public static StockOperationException reserveStockFailed(List<String> errors) {
        return new StockOperationException(format(STOCK_RESERVATION_FAILURE_MSG, String.join(", ", errors)));
    }

    public static StockOperationException executeReservationFailed(List<String> errors) {
        return new StockOperationException(format(EXECUTE_RESERVATION_FAILURE_MSG, String.join(", ", errors)));
    }
}
