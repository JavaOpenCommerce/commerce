package com.example.utils.converters;

import com.example.business.models.ProducerDetailModel;
import com.example.business.models.ProducerModel;
import com.example.database.entity.ProducerDetails;

import java.util.Objects;


public interface ProducerDetailConverter {

    static ProducerDetailModel convertToModel(ProducerDetails details) {
        return ProducerDetailModel.builder()
                .id(details.getId())
                .name(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .build();
    }

    static ProducerDetailModel getProducerDetailByLanguage(ProducerModel producer, String lang, String defaultLang) {
        if (producer.getDetails().isEmpty()) {
            return ProducerDetailModel.builder().name("Error").build();
        }

        return producer.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(producer.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(ProducerDetailModel.builder().name("Error").build()));
    }
}
