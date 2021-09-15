package com.example.javaopencommerce.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.item.ItemEntity;
import com.example.javaopencommerce.item.ItemDetails;
import com.example.javaopencommerce.item.ItemDetailsEntity;
import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.item.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ItemConverter {


    static Item convertToModel(ItemEntity item, List<ItemDetailsEntity> itemDetails) {

        List<ItemDetails> details = itemDetails.stream()
                .map(ItemDetailConverter::convertToModel)
                .collect(Collectors.toList());

        return Item.builder()
                .id(item.getId())
                .valueGross(Value.of(item.getValueGross()))
                .vat(Vat.of(item.getVat()))
                .details(details)
                .stock(item.getStock())
                .image(ImageConverter.convertToModel(
                        ofNullable(item.getImage()).orElse(ImageEntity.builder().build())))
                .build();
    }

    static Item convertDtoToModel(ItemDto item) {
        return Item.builder()
                .id(item.getId())
                .stock(item.getStock())
                .valueGross(Value.of(item.getValueGross()))
                .vat(Vat.of(item.getVat()))
                .image(ImageConverter.convertDtoToModel(item.getImage()))
                .build();
    }

    static ItemEntity convertModelToEntity(Item item) {
        return ItemEntity.builder()
                .id(item.getId())
                .stock(item.getStock())
                .valueGross(item.getValueGross().asDecimal())
                .vat(item.getVat().asDouble())
                .image(ImageConverter.convertModelToEntity(item.getImage()))
                .details(emptyList())
                .build();
    }

    static List<Item> convertToItemModelList(List<ItemEntity> items,
            List<ItemDetailsEntity> itemDetails) {

        List<Item> itemModels = new ArrayList<>();
        for (ItemEntity item : items) {
            List<ItemDetailsEntity> itemDetailsFiltered = itemDetails.stream()
                    .filter(id -> id.getItemId().equals(item.getId()))
                    .collect(toList());

            itemModels
                    .add(convertToModel(item, itemDetailsFiltered));
        }
        return itemModels;
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
