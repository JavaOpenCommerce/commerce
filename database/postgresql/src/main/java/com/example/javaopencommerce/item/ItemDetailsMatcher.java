package com.example.javaopencommerce.item;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;

interface ItemDetailsMatcher {

  static List<Item> convertToItemModelList(List<ItemEntity> items,
      List<ItemDetailsEntity> itemDetails) {

    return items.stream().map(item -> {
      Item itemModel = item.toItemModel();
      itemModel.addDetails(resolveItemDetails(itemDetails, item));
      return itemModel;
    }).collect(toList());
  }

  private static List<ItemDetails> resolveItemDetails(List<ItemDetailsEntity> itemDetails,
      ItemEntity item) {
    return itemDetails.stream()
        .filter(details -> details.getItemId().equals(item.getId()))
        .map(ItemDetailsEntity::toItemDetailsModel)
        .collect(toUnmodifiableList());
  }
}
