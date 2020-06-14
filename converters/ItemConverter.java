package com.example.business.converters;

import com.example.business.models.ItemModel;
import com.example.database.entity.Item;

public interface ItemConverter {

    static ItemModel convertToModel(Item item) {
        return ItemModel.builder()
                .id(item.getId())
                .name(item.getName())
                .valueGross(item.getValueGross())
                .description(item.getDescription())
                .producerModel(ProducerConverter.convertToModel(item.getProducer()))
                .build();
    }
}
