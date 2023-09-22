package com.example.opencommerce.app.pricing.query;

import com.example.opencommerce.domain.ItemId;

import java.util.List;

public interface PriceQueryRepository {

    PriceDto getPriceByItemId(ItemId itemId);

    List<PriceDto> getPricesForItemsWithIds(List<ItemId> ids);
}
