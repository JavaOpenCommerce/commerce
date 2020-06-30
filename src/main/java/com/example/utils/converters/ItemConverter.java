package com.example.utils.converters;

import com.example.business.Value;
import com.example.business.Vat;
import com.example.business.models.CategoryModel;
import com.example.business.models.ItemDetailModel;
import com.example.business.models.ItemModel;
import com.example.database.entity.Item;
import com.example.rest.dtos.ItemDto;

import java.util.Set;
import java.util.stream.Collectors;

public interface ItemConverter {


    static ItemModel convertToModel(Item item) {

        Set<CategoryModel> categoryModels = item.getCategory()
                .stream()
                .map(category -> CategoryConverter.convertToModel(category))
                .collect(Collectors.toSet());

        Set<ItemDetailModel> itemDetailModels = item.getDetails().stream()
                .map(itemDetails -> ItemDetailConverter.convertToModel(itemDetails))
                .collect(Collectors.toSet());

        return ItemModel.builder()
                .id(item.getId())
                .valueGross(Value.of(item.getValueGross()))
                .producer(ProducerConverter.convertToModel(item.getProducer()))
                .category(categoryModels)
                .vat(Vat.of(item.getVat()))
                .details(itemDetailModels)
                .stock(item.getStock())
                .image(ImageConverter.convertToModel(item.getImage()))
                .build();
    }

    static ItemDto convertToDto(ItemModel item, String lang, String defaultLang) {

        ItemDetailModel details = ItemDetailConverter.getItemDetailsByLanguage(item, lang, defaultLang);

        return ItemDto.builder()
                .id(item.getId())
                .name(details.getName())
                .valueGross(item.getValueGross().asDecimal())
                .producer(ProducerConverter.convertToDto(item.getProducer(), lang, defaultLang))
                .vat(item.getVat().asDouble())
                .image(ImageConverter.convertToDto(item.getImage()))
                .build();
    }

}
