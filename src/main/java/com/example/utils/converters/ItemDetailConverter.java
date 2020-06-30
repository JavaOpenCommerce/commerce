package com.example.utils.converters;

import com.example.business.models.ImageModel;
import com.example.business.models.ItemDetailModel;
import com.example.business.models.ItemModel;
import com.example.database.entity.ItemDetails;
import com.example.rest.dtos.ImageDto;
import com.example.rest.dtos.ItemDetailDto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface ItemDetailConverter {

    static ItemDetailModel convertToModel(ItemDetails details) {

        Set<ImageModel> images = details.getImages().stream()
                .map(i -> ImageConverter.convertToModel(i))
                .collect(Collectors.toSet());

        return ItemDetailModel.builder()
                .name(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .additionalImages(images)
                .build();
    }

    static ItemDetailDto convertToDto(ItemModel item, String lang, String defaultLang) {

        ItemDetailModel details = getItemDetailsByLanguage(item, lang, defaultLang);

        Set<ImageDto> images = details.getAdditionalImages().stream()
                .map(i -> ImageConverter.convertToDto(i))
                .collect(Collectors.toSet());

        return ItemDetailDto.builder()
                .id(item.getId())
                .valueGross(item.getValueGross().asDecimal())
                .vat(item.getVat().asDouble())
                .producer(ProducerConverter.convertToDto(item.getProducer(), lang, defaultLang))
                .stock(item.getStock())
                .mainImage(ImageConverter.convertToDto(item.getImage()))
                .name(details.getName())
                .description(details.getDescription())
                .additionalImages(images)
                .build();
    }

    static ItemDetailModel getItemDetailsByLanguage(ItemModel item, String lang, String defaultLang) {
        if (item.getDetails().isEmpty()) {
            return ItemDetailModel.builder().name("Error").build();
        }

        return item.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(item.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(ItemDetailModel.builder().name("Error").build()));
    }
}
