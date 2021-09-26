package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.item.dtos.ItemDetailsDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import java.util.List;
import java.util.stream.Collectors;

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
        .image(ImageDto.fromSnapshot(itemSnapshot.getImage()))
        .build();
  }

  ItemDetailsDto itemToDetailsDto(ItemSnapshot itemSnapshot) {
    ItemDetailsSnapshot details = itemDetailsLangResolver.resolveDetails(itemSnapshot);
    List<ImageDto> additionalImages = details.getAdditionalImages().stream()
        .map(ImageDto::fromSnapshot)
        .collect(Collectors.toList());

    return ItemDetailsDto.builder()
        .id(itemSnapshot.getId())
        .stock(itemSnapshot.getStock())
        .valueGross(itemSnapshot.getValueGross().asDecimal())
        .vat(itemSnapshot.getVat().asDouble())
        .description(details.getDescription())
        .name(details.getName())
        .mainImage(ImageDto.fromSnapshot(itemSnapshot.getImage()))
        .additionalImages(additionalImages)
        .build();
  }
}
