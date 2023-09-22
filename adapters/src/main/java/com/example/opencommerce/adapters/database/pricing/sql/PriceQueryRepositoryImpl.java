package com.example.opencommerce.adapters.database.pricing.sql;

import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.pricing.ItemPrice;
import com.example.opencommerce.domain.pricing.PriceEventStore;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
class PriceQueryRepositoryImpl implements PriceQueryRepository {

    private final PriceEventStore store;

    PriceQueryRepositoryImpl(PriceEventStore store) {
        this.store = store;
    }

    @Override
    public PriceDto getPriceByItemId(ItemId itemId) {
        ItemPrice price = store.getPriceByItemId(itemId);
        return PriceDto.fromSnapshot(price.getSnapshot());
    }

    @Override
    public List<PriceDto> getPricesForItemsWithIds(List<ItemId> ids) {
        return ids.stream()
                .map(store::getPriceByItemId)
                .map(ItemPrice::getSnapshot)
                .map(PriceDto::fromSnapshot)
                .toList();
    }
}
