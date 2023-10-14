package com.example.opencommerce.app.pricing;

import com.example.opencommerce.app.pricing.commands.ApplyDiscountCommand;
import com.example.opencommerce.app.pricing.commands.RemoveDiscountCommand;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.pricing.ItemPrice;
import com.example.opencommerce.domain.pricing.PriceEventStore;
import com.example.opencommerce.domain.pricing.events.DiscountAppliedEvent;
import com.example.opencommerce.domain.pricing.events.DiscountRemovedEvent;

public class DiscountScenarios {

    private final PriceEventStore store;

    public DiscountScenarios(PriceEventStore store) {
        this.store = store;
    }

    public void applyDiscount(ApplyDiscountCommand command) {
        ItemId id = command.id();
        ItemPrice price = store.getPriceByItemId(id);
        OperationResult<DiscountAppliedEvent> result = price.addNewDiscount(command.discountValue(), command.executionDate());
        if (!result.successful()) {
            throw new PriceOperationException(result.getErrors());
        }
        store.saveEvent(id, result.result());
    }

    public void removeDiscount(RemoveDiscountCommand command) {
        ItemId id = command.id();
        ItemPrice price = store.getPriceByItemId(id);
        OperationResult<DiscountRemovedEvent> result = price.removeCurrentDiscount(command.executionDate());
        if (!result.successful()) {
            throw new PriceOperationException(result.getErrors());
        }
        store.saveEvent(id, result.result());
    }
}
