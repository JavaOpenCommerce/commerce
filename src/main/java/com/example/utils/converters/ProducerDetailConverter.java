package com.example.utils.converters;

import com.example.javaopencommerce.producer.ProducerDetailModel;
import com.example.javaopencommerce.producer.ProducerDetails;
import com.example.javaopencommerce.producer.ProducerModel;
import io.netty.handler.codec.http.HttpResponseStatus;
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
            return ProducerDetailModel.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build();
        }

        return producer.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(producer.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(ProducerDetailModel.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build()));
    }
}
