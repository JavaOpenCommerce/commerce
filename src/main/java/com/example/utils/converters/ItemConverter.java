package com.example.utils.converters;

import com.example.business.Value;
import com.example.business.Vat;
import com.example.business.models.CategoryModel;
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

        return ItemModel.builder()
                .id(item.getId())
                .name(item.getName())
                .valueGross(Value.of(item.getValueGross()))
                .description(item.getDescription())
                .producerModel(ProducerConverter.convertToModel(item.getProducer()))
                .category(categoryModels)
                .vat(Vat.of(item.getVat()))
                .build();
    }

    static ItemDto convertToDto(ItemModel itemModel) {
        return ItemDto.builder()
                .id(itemModel.getId())
                .name(itemModel.getName())
                .description(itemModel.getDescription())
                .valueGross(itemModel.getValueGross().asDecimal())
                .vat(itemModel.getVat().asDouble())
                .build();
    }
}
