package com.example.opencommerce.app.order;

import com.example.opencommerce.app.catalog.query.FullItemDto;
import com.example.opencommerce.app.catalog.query.ImageDto;
import com.example.opencommerce.app.catalog.query.ItemDto;
import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import com.example.opencommerce.domain.order.Item;

import static java.util.Optional.ofNullable;

class ItemMapper {

    private static final String EMPTY_URL = "";

    Item toModel(ItemDto dto, Amount stock, PriceDto price) {
        return new Item(ItemId.of(dto.getId()), stock, dto.getName(),
                ofNullable(dto.getImage()).map(ImageDto::getUrl)
                        .orElse(EMPTY_URL),
                Value.of(price.getFinalPrice()), Vat.of(price.getVat()));
    }

    Item toModel(FullItemDto dto, Amount stock, PriceDto price) {
        return new Item(ItemId.of(dto.getId()), stock, dto.getName(),
                ofNullable(dto.getMainImage()).map(ImageDto::getUrl)
                        .orElse(EMPTY_URL),
                Value.of(price.getFinalPrice()), Vat.of(price.getVat()));
    }

}
