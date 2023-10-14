package com.example.opencommerce.app.pricing.query;

import com.example.opencommerce.domain.ItemId;

import java.util.List;
import java.util.Map;

public interface PriceQueryRepository {

    PriceDto getPriceByItemId(ItemId itemId);

    Map<ItemId, PriceDto> getPricesForItemsWithIds(List<ItemId> ids);
}
