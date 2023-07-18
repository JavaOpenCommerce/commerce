package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ImageDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;

import static java.util.Optional.ofNullable;

public class ItemMapper {

    private static final String EMPTY_URL = "";

    Item toModel(ItemDto dto, Amount stock) {
        return new Item(ItemId.of(dto.getId()), stock, dto.getName(),
                ofNullable(dto.getImage()).map(ImageDto::getUrl)
                        .orElse(EMPTY_URL),
                Value.of(dto.getValueGross()), Vat.of(dto.getVat()));
    }

    Item toModel(FullItemDto dto, Amount stock) {
        return new Item(ItemId.of(dto.getId()), stock, dto.getName(),
                ofNullable(dto.getMainImage()).map(ImageDto::getUrl)
                        .orElse(EMPTY_URL),
                Value.of(dto.getValueGross()), Vat.of(dto.getVat()));
    }

}
