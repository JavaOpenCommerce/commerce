package com.example.javaopencommerce.catalog;

import java.util.List;

interface PsqlItemRepository {

  List<ItemEntity> getAllItems();

  ItemEntity getItemById(Long id);

  List<ItemEntity> getItemsByIdList(List<Long> ids);

  List<ItemEntity> getAllShippingMethods();

  ItemEntity saveItem(ItemEntity item);

  Integer getItemStock(Long id);

  Integer changeItemStock(Long id, int stock);
}