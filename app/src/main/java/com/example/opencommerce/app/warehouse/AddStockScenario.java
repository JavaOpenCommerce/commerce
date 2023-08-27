package com.example.opencommerce.app.warehouse;

import com.example.opencommerce.app.warehouse.exceptions.StockOperationException;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.warehouse.ItemStock;
import com.example.opencommerce.domain.warehouse.WarehouseItemRepository;

public class AddStockScenario {

    private final WarehouseItemRepository repository;

    public AddStockScenario(WarehouseItemRepository repository) {
        this.repository = repository;
    }

    public void addToStock(ItemId itemId, Amount stockToAdd) {
        ItemStock item = repository.getItemById(itemId);

        OperationResult<ItemStock> operationResult = item.increaseStockBy(stockToAdd);

        if (!operationResult.successful()) {
            throw StockOperationException.addStockFailed(operationResult.getErrors());
        }
        repository.updateStock(item);
    }
}
