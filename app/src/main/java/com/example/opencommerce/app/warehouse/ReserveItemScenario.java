package com.example.opencommerce.app.warehouse;

import com.example.opencommerce.app.warehouse.exceptions.StockOperationException;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.warehouse.ItemReservation;
import com.example.opencommerce.domain.warehouse.ItemStock;
import com.example.opencommerce.domain.warehouse.WarehouseItemRepository;

public class ReserveItemScenario {

    private final WarehouseItemRepository repository;

    public ReserveItemScenario(WarehouseItemRepository repository) {
        this.repository = repository;
    }

    public void reserve(ReserveItemCommand command) {
        ItemStock item = repository.getItemById(command.itemId());
        ItemReservation reservation = ItemReservation.newReservation(command.orderId(), command.amountToReserve());
        OperationResult<ItemStock> operationResult = item.makeStockReservation(reservation);
        if (!operationResult.successful()) {
            throw StockOperationException.reserveStockFailed(operationResult.getErrors());
        }
        repository.save(item);
    }

    public record ReserveItemCommand(ItemId itemId, OrderId orderId, Amount amountToReserve) {
    }
}
