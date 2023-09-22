package com.example.opencommerce.app.warehouse;

import com.example.opencommerce.app.warehouse.exceptions.StockOperationException;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.warehouse.ItemStock;
import com.example.opencommerce.domain.warehouse.WarehouseItemRepository;

public class ExecuteItemReservationScenario {

    private final WarehouseItemRepository repository;

    public ExecuteItemReservationScenario(WarehouseItemRepository repository) {
        this.repository = repository;
    }

    public void executeItemReservation(ItemId itemId, OrderId orderId) {
        ItemStock item = repository.getItemById(itemId);
        OperationResult<ItemStock> operationResult = item.executeReservationFromOrderWithId(orderId);
        if (!operationResult.successful()) {
            throw StockOperationException.executeReservationFailed(operationResult.getErrors());
        }
        repository.save(item);
    }
}
