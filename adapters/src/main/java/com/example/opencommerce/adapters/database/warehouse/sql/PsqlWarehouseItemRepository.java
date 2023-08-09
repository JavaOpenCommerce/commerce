package com.example.opencommerce.adapters.database.warehouse.sql;

import java.util.List;

interface PsqlWarehouseItemRepository {

    ItemStockEntity getItemById(Long id);

    List<ItemStockEntity> getItemStocksByIdList(List<Long> ids);

    void updateStock(Long id, Integer totalStock);

    void save(WarehouseItemEntity item);

    void save(ItemStockEntity item);
}
