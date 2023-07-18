package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.OperationResult;
import com.example.javaopencommerce.warehouse.exceptions.StockOperationException;

public class AddStockScenario {

    private final WarehouseItemRepository repository;

    public AddStockScenario(WarehouseItemRepository repository) {
        this.repository = repository;
    }

    public void addToStock(ItemId itemId, Amount stockToAdd) {
        ItemStock item = repository.getItemById(itemId);

        OperationResult operationResult = item.increaseStockBy(stockToAdd);

        if (!operationResult.succesful()) {
            throw StockOperationException.addStockFailed(operationResult.getErrors());
        }
        repository.updateStock(item);
    }
}
