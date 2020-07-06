package com.example.utils.converters;

import com.example.business.Value;
import com.example.business.Vat;
import com.example.business.models.CategoryModel;
import com.example.business.models.ItemDetailModel;
import com.example.business.models.ItemModel;
import com.example.database.entity.Image;
import com.example.database.entity.Item;
import com.example.database.entity.Producer;
import com.example.rest.dtos.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

public interface ItemConverter {


    static ItemModel convertToModel(Item item) {

        List<CategoryModel> categoryModels = ofNullable(item.getCategory()).orElse(emptyList())
                .stream()
                .map(category -> CategoryConverter.convertToModel(category))
                .collect(Collectors.toList());

        List<ItemDetailModel> itemDetailModels = ofNullable(item.getDetails()).orElse(emptyList())
                .stream()
                .map(itemDetails -> ItemDetailConverter.convertToModel(itemDetails))
                .collect(Collectors.toList());

        return ItemModel.builder()
                .id(item.getId())
                .valueGross(Value.of(item.getValueGross()))
                .producer(
                        ProducerConverter.convertToModel(
                                ofNullable(item.getProducer()).orElse(Producer.builder().build())))
                .category(categoryModels)
                .vat(Vat.of(item.getVat()))
                .details(itemDetailModels)
                .stock(item.getStock())
                .image(ImageConverter.convertToModel(
                        ofNullable(item.getImage()).orElse(Image.builder().build())))
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
