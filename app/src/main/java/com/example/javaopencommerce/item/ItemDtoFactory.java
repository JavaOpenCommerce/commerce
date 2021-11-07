package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.ItemSnapshot.ItemDetailsSnapshot;
import com.example.javaopencommerce.item.dtos.ItemDetailsDto;
import com.example.javaopencommerce.item.dtos.ItemDto;

class ItemDtoFactory {

  private final ItemDetailsLangResolver itemDetailsLangResolver;

  ItemDtoFactory(ItemDetailsLangResolver itemDetailsLangResolver) {
    this.itemDetailsLangResolver = itemDetailsLangResolver;
  }

  ItemDto itemToDto(ItemSnapshot itemSnapshot) {
    ItemDetailsSnapshot itemDetailsSnapshot =
        itemDetailsLangResolver.resolveDetails(itemSnapshot);
    return ItemDto.builder()
        .id(itemSnapshot.getId())
        .stock(itemSnapshot.getStock())
        .valueGross(itemSnapshot.getValueGross().asDecimal())
        .vat(itemSnapshot.getVat().asDouble())
        .name(itemDetailsSnapshot.getName())
        .imageId(itemSnapshot.getImageId())
        .build();
  }

  ItemDetailsDto itemToDetailsDto(ItemSnapshot itemSnapshot) {
    ItemDetailsSnapshot details = itemDetailsLangResolver.resolveDetails(itemSnapshot);

    return ItemDetailsDto.builder()
        .id(itemSnapshot.getId())
        .stock(itemSnapshot.getStock())
        .valueGross(itemSnapshot.getValueGross().asDecimal())
        .vat(itemSnapshot.getVat().asDouble())
        .description(details.getDescription())
        .name(details.getName())
        .mainImageId(itemSnapshot.getImageId())
        .additionalImageIds(details.getAdditionalImageIds())
        .build();
  }
}
