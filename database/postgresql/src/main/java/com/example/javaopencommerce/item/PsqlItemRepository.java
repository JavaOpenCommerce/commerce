package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface PsqlItemRepository {

  Uni<List<ItemEntity>> getAllItems();

  Uni<ItemEntity> getItemById(Long id);

  Uni<List<ItemEntity>> getItemsListByIdList(List<Long> ids);

  Uni<List<ItemEntity>> getAllShippingMethods();

  Uni<List<ItemDetailsEntity>> getAllDetailsForShippingMethods();

  Uni<List<ItemDetailsEntity>> getAllItemDetails();

  Uni<List<ItemDetailsEntity>> getItemDetailsListByItemId(Long id);

  Uni<List<ItemDetailsEntity>> getItemDetailsListByIdList(List<Long> ids);

  Uni<ItemEntity> saveItem(ItemEntity item);

  Uni<ItemDetailsEntity> saveItemDetails(ItemDetailsEntity itemDetails);

  Uni<Integer> getItemStock(Long id);

  Uni<Integer> changeItemStock(Long id, int stock);
}