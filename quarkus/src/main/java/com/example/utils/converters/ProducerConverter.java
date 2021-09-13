package com.example.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.producer.Producer;
import com.example.javaopencommerce.producer.ProducerDetailModel;
import com.example.javaopencommerce.producer.ProducerDto;
import com.example.javaopencommerce.producer.ProducerModel;
import java.util.List;
import java.util.stream.Collectors;

public interface ProducerConverter {

    static ProducerModel convertToModel(Producer producer) {

        List<ProducerDetailModel> details = ofNullable(producer.getDetails()).orElse(emptyList())
                .stream()
                .map(ProducerDetailConverter::convertToModel)
                .collect(Collectors.toList());

        return ProducerModel.builder()
                .id(producer.getId())
                .details(details)
                .image(ImageConverter.convertToModel(
                        ofNullable(producer.getImage()).orElse(Image.builder().build())))
                .build();
    }

    static ProducerDto convertToDto(ProducerModel producer, String lang, String defaultLang) {
        if (producer.getDetails().isEmpty()) {
            return ProducerDto.builder().name("404").build();
        }

        ProducerDetailModel details = ProducerDetailConverter.getProducerDetailByLanguage(producer, lang, defaultLang);

        return ProducerDto.builder()
                .id(producer.getId())
                .name(details.getName())
                .description(details.getDescription())
                .image(ImageConverter.convertToDto(producer.getImage()))
                .build();
    }
}
