package com.example.javaopencommerce.item;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.utils.converters.ImageConverter;

public interface ItemConverter {

    static Item convertDtoToModel(ItemDto item) {
        return Item.builder()
                .id(item.getId())
                .stock(item.getStock())
                .valueGross(Value.of(item.getValueGross()))
                .vat(Vat.of(item.getVat()))
                .image(ImageConverter.convertDtoToModel(item.getImage()))
                .build();
    }

    static ItemDto convertToDto(Item item, String lang, String defaultLang) {

        ItemDetails details = ItemDetailConverter.getItemDetailsByLanguage(item, lang, defaultLang);

        return ItemDto.builder()
                .id(item.getId())
                .stock(item.getStock())
                .name(details.getName())
                .valueGross(item.getValueGross().asDecimal())
                .vat(item.getVat().asDouble())
                .image(ImageConverter.convertToDto(item.getImage()))
                .build();
    }

}
