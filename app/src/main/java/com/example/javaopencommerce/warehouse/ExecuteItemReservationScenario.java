package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.OperationResult;
import com.example.javaopencommerce.OrderId;
import com.example.javaopencommerce.warehouse.exceptions.StockOperationException;

public class ExecuteItemReservationScenario {

    private final WarehouseItemRepository repository;

    public ExecuteItemReservationScenario(WarehouseItemRepository repository) {
        this.repository = repository;
    }

    public void executeItemReservation(ItemId itemId, OrderId orderId) {
        ItemStock item = repository.getItemById(itemId);
        OperationResult operationResult = item.executeReservationFromOrderWithId(orderId);
        if (!operationResult.succesful()) {
            throw StockOperationException.executeReservationFailed(operationResult.getErrors());
        }
        repository.save(item);
    }
}
