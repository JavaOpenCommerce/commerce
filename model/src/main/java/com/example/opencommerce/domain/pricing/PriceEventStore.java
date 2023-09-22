package com.example.opencommerce.domain.pricing;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.pricing.events.PriceEvent;

public interface PriceEventStore {

    void saveEvent(ItemId itemId, PriceEvent event);

    ItemPrice getPriceByItemId(ItemId id);
}
