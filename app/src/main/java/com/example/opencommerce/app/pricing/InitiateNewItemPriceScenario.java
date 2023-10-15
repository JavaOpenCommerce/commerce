package com.example.opencommerce.app.pricing;

import com.example.opencommerce.app.pricing.commands.InitiateNewPriceCommand;
import com.example.opencommerce.app.pricing.exceptions.PriceOperationException;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.pricing.ItemPrice;
import com.example.opencommerce.domain.pricing.PriceEventStore;
import com.example.opencommerce.domain.pricing.events.NewPriceInitiatedEvent;

public class InitiateNewItemPriceScenario {

    private final PriceEventStore store;

    public InitiateNewItemPriceScenario(PriceEventStore store) {
        this.store = store;
    }

    public void initiateNewPrice(InitiateNewPriceCommand command) {
        ItemId id = command.id();
        ItemPrice newPrice = ItemPrice.create(id);
        OperationResult<NewPriceInitiatedEvent> result = newPrice.initiate(command.priceNett(), command.vat());
        if (result.successful()) {
            store.saveEvent(id, result.result());
        } else {
            throw new PriceOperationException(result.getErrors());
        }
    }
}
