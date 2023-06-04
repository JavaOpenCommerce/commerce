package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.Item.ItemDetails;
import java.util.List;

interface ItemRepository {

  List<Item> getAllItems();

  Item getItemById(Long id);

  List<Item> getItemsByIdList(List<Long> ids);

  Item saveItem(Item item);

  ItemDetails saveItemDetails(Item itemDetails);

  Integer getItemStock(Long id);

  Integer changeItemStock(Long id, int stock);

}
