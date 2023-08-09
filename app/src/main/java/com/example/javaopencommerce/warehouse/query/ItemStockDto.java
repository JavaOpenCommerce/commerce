package com.example.javaopencommerce.warehouse.query;

import java.util.List;
import java.util.UUID;

public record ItemStockDto(Long itemId, Integer totalStock, Integer availableStock,
                           List<StockReservationDto> reservations) {

    public record StockReservationDto(UUID orderId, int amountReserved) {
    }

}
