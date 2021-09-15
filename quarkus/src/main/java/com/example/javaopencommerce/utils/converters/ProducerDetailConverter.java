package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.producer.Producer;
import com.example.javaopencommerce.producer.ProducerDetails;
import com.example.javaopencommerce.producer.ProducerDetailsEntity;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Objects;


public interface ProducerDetailConverter {

    static ProducerDetails convertToModel(ProducerDetailsEntity details) {
        return ProducerDetails.builder()
                .id(details.getId())
                .name(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .build();
    }

    static ProducerDetails getProducerDetailByLanguage(Producer producer, String lang, String defaultLang) {
        if (producer.getDetails().isEmpty()) {
            return ProducerDetails.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build();
        }

        return producer.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(producer.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(
                            ProducerDetails.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build()));
    }
}
