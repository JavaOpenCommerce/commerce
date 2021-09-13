package com.example.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.item.ItemDetailModel;
import com.example.javaopencommerce.item.ItemDetails;
import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.item.ItemModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ItemConverter {


    static ItemModel convertToModel(Item item, List<ItemDetails> itemDetails) {

        List<ItemDetailModel> details = itemDetails.stream()
                .map(ItemDetailConverter::convertToModel)
                .collect(Collectors.toList());

        return ItemModel.builder()
                .id(item.getId())
                .valueGross(Value.of(item.getValueGross()))
                .vat(Vat.of(item.getVat()))
                .details(details)
                .stock(item.getStock())
                .image(ImageConverter.convertToModel(
                        ofNullable(item.getImage()).orElse(Image.builder().build())))
                .build();
    }

    static ItemModel convertDtoToModel(ItemDto item) {
        return ItemModel.builder()
                .id(item.getId())
                .stock(item.getStock())
                .valueGross(Value.of(item.getValueGross()))
                .vat(Vat.of(item.getVat()))
                .image(ImageConverter.convertDtoToModel(item.getImage()))
                .build();
    }

    static Item convertModelToEntity(ItemModel item) {
        return Item.builder()
                .id(item.getId())
                .stock(item.getStock())
                .valueGross(item.getValueGross().asDecimal())
                .vat(item.getVat().asDouble())
                .image(ImageConverter.convertModelToEntity(item.getImage()))
                .details(emptyList())
                .build();
    }

    static List<ItemModel> convertToItemModelList(List<Item> items,
            List<ItemDetails> itemDetails) {

        List<ItemModel> itemModels = new ArrayList<>();
        for (Item item : items) {
            List<ItemDetails> itemDetailsFiltered = itemDetails.stream()
                    .filter(id -> id.getItemId().equals(item.getId()))
                    .collect(toList());

            itemModels
                    .add(convertToModel(item, itemDetailsFiltered));
        }
        return itemModels;
    }

    static ItemDto convertToDto(ItemModel item, String lang, String defaultLang) {

        ItemDetailModel details = ItemDetailConverter.getItemDetailsByLanguage(item, lang, defaultLang);

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
