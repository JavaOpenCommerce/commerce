package com.example.javaopencommerce.order;

import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ImageDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;

public class ItemMapper {

  private static final String EMPTY_URL = "";

  Item fromCatalog(ItemDto dto) {
    return new Item(ItemId.of(dto.getId()), Amount.of(dto.getStock()), dto.getName(),
        ofNullable(dto.getImage()).map(ImageDto::getUrl).orElse(EMPTY_URL),
        Value.of(dto.getValueGross()), Vat.of(dto.getVat()));
  }

  Item fromCatalog(FullItemDto dto) {
    return new Item(ItemId.of(dto.getId()), Amount.of(dto.getStock()), dto.getName(),
        ofNullable(dto.getMainImage()).map(ImageDto::getUrl).orElse(EMPTY_URL),
        Value.of(dto.getValueGross()), Vat.of(dto.getVat()));
  }

}
