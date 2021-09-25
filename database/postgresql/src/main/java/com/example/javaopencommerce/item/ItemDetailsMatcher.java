package com.example.javaopencommerce.item;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.ArrayList;
import java.util.List;

interface ItemDetailsMatcher {
  static List<Item> convertToItemModelList(List<ItemEntity> items,
      List<ItemDetailsEntity> itemDetails) {

    List<Item> itemModels = new ArrayList<>();
    for (ItemEntity item : items) {
      List<ItemDetails> itemDetailsFiltered = itemDetails.stream()
          .filter(id -> id.getItemId().equals(item.getId()))
          .map(ItemDetailsEntity::toItemDetailsModel)
          .collect(toUnmodifiableList());

      Item itemModel = item.toItemModel();
      itemModel.addDetails(itemDetailsFiltered);

      itemModels
          .add(itemModel);
    }
    return itemModels;
  }
}
