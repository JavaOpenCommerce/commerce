package com.example.javaopencommerce.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.producer.Producer;
import com.example.javaopencommerce.producer.ProducerDetails;
import com.example.javaopencommerce.producer.ProducerDto;
import com.example.javaopencommerce.producer.ProducerEntity;
import java.util.List;
import java.util.stream.Collectors;

public interface ProducerConverter {

    static Producer convertToModel(ProducerEntity producer) {

        List<ProducerDetails> details = ofNullable(producer.getDetails()).orElse(emptyList())
                .stream()
                .map(ProducerDetailConverter::convertToModel)
                .collect(Collectors.toList());

        return Producer.builder()
                .id(producer.getId())
                .details(details)
                .image(ofNullable(producer.getImage()).orElse(ImageEntity.builder().build()).toImageModel())
                .build();
    }

    static ProducerDto convertToDto(Producer producer, String lang, String defaultLang) {
        if (producer.getDetails().isEmpty()) {
            return ProducerDto.builder().name("404").build();
        }

        ProducerDetails details = ProducerDetailConverter.getProducerDetailByLanguage(producer, lang, defaultLang);

        return ProducerDto.builder()
                .id(producer.getId())
                .name(details.getName())
                .description(details.getDescription())
                .image(ImageDto.fromSnapshot(producer.getImage().getSnapshot()))
                .build();
    }
}
