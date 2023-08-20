package com.example.opencommerce.app.warehouse.query;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;

import java.util.List;
import java.util.Map;

public interface WarehouseQueryRepository {
    ItemStockDto getItemStockById(ItemId id);

    Map<ItemId, Amount> getAvailableStocksByItemIds(List<ItemId> ids);

    Amount getAvailableStockById(ItemId id);

}
