package com.example.business.converters;

import com.example.business.models.ProducerModel;
import com.example.database.entity.Producer;

public interface ProducerConverter {

    static ProducerModel convertToModel(Producer producer) {
        return ProducerModel.builder()
                .id(producer.getId())
                .name(producer.getName())
                .description(producer.getDescription())
                .logoUrl(producer.getLogoUrl())
                .build();
    }
}
