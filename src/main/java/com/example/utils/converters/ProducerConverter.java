package com.example.utils.converters;

import com.example.business.models.ProducerModel;
import com.example.database.entity.Producer;
import com.example.rest.dtos.ProducerDto;

public interface ProducerConverter {

    static ProducerModel convertToModel(Producer producer) {
        return ProducerModel.builder()
                .id(producer.getId())
                .lang(producer.getLang())
                .name(producer.getName())
                .description(producer.getDescription())
                .image(ImageConverter.convertToModel(producer.getImage()))
                .build();
    }

    static ProducerDto convertToDto(ProducerModel producer) {
        return ProducerDto.builder()
                .id(producer.getId())
                .lang(producer.getLang())
                .name(producer.getName())
                .description(producer.getDescription())
                .image(ImageConverter.convertToDto(producer.getImage()))
                .build();
    }
}
