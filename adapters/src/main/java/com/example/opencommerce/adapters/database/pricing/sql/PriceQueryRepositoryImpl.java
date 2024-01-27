package com.example.opencommerce.adapters.database.pricing.sql;

import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.pricing.ItemPrice;
import com.example.opencommerce.domain.pricing.PriceEventStore;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Map<ItemId, PriceDto> getPricesForItemsWithIds(List<ItemId> ids) {
        return ids.stream()
                .map(store::getPriceByItemId)
                .map(ItemPrice::getSnapshot)
                .map(PriceDto::fromSnapshot)
                .collect(Collectors.toMap(PriceDto::getItemId, price -> price));
    }
}
