package com.example.opencommerce.app.pricing;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.pricing.PriceEventStore;
import com.example.opencommerce.domain.pricing.PriceSnapshot;

public class GetPriceForItemScenario {

    private final PriceEventStore store;

    public GetPriceForItemScenario(PriceEventStore store) {
        this.store = store;
    }

    public PriceSnapshot getPriceForItemWithId(ItemId itemId) {
        return this.store.getPriceByItemId(itemId)
                .getSnapshot();
    }
}