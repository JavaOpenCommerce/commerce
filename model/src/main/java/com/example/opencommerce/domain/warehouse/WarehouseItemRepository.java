package com.example.opencommerce.domain.warehouse;

import com.example.opencommerce.domain.ItemId;

public interface WarehouseItemRepository {

    ItemStock getItemById(ItemId id);

    void updateStock(ItemStock item);

    void save(ItemStock item);
}
