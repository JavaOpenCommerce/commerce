package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.OperationResult;
import com.example.javaopencommerce.OrderId;
import com.example.javaopencommerce.warehouse.exceptions.StockOperationException;

public class ReserveItemScenario {

    private final WarehouseItemRepository repository;

    public ReserveItemScenario(WarehouseItemRepository repository) {
        this.repository = repository;
    }

    public void reserve(ReserveItemCommand command) {
        ItemStock item = repository.getItemById(command.itemId());
        ItemReservation reservation = ItemReservation.newReservation(command.orderId(), command.amountToReserve());
        OperationResult operationResult = item.makeStockReservation(reservation);
        if (!operationResult.succesful()) {
            throw StockOperationException.reserveStockFailed(operationResult.getErrors());
        }
        repository.save(item);
    }

    public record ReserveItemCommand(ItemId itemId, OrderId orderId, Amount amountToReserve) {
    }
}
