package com.example.javaopencommerce.warehouse.query;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;

import java.util.List;
import java.util.Map;

public interface WarehouseQueryRepository {
    ItemStockDto getItemStockById(ItemId id);

    Map<ItemId, Amount> getAvailableStocksByItemIds(List<ItemId> ids);

    Amount getAvailableStockById(ItemId id);

}
