package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface ItemRepository {

  Uni<List<Item>> getAllItems();

  Uni<Item> getItemById(Long id);

  Uni<List<Item>> getItemsByIdList(List<Long> ids);

  Uni<Item> saveItem(Item item);

  Uni<ItemDetails> saveItemDetails(Item itemDetails);

  Uni<Integer> getItemStock(Long id);

  Uni<Integer> changeItemStock(Long id, int stock);

}
