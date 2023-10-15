package com.example.opencommerce.app.pricing;

import com.example.opencommerce.app.pricing.commands.ChangeBaseItemPriceCommand;
import com.example.opencommerce.app.pricing.exceptions.PriceOperationException;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.pricing.ItemPrice;
import com.example.opencommerce.domain.pricing.PriceEventStore;
import com.example.opencommerce.domain.pricing.events.BasePriceChangedEvent;

public class ChangeItemPriceScenario {

    private final PriceEventStore store;

    public ChangeItemPriceScenario(PriceEventStore store) {
        this.store = store;
    }

    public void changeBaseItemPrice(ChangeBaseItemPriceCommand command) {
        ItemId id = command.id();
        ItemPrice priceByItemId = store.getPriceByItemId(id);
        OperationResult<BasePriceChangedEvent> result = priceByItemId.changeBasePrice(command.newBasePrice(), command.executionDate());
        if (result.successful()) {
            store.saveEvent(id, result.result());
        } else {
            throw new PriceOperationException(result.getErrors());
        }
    }
}
