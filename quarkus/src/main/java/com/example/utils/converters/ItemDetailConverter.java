package com.example.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.image.ImageModel;
import com.example.javaopencommerce.item.ItemDetailDto;
import com.example.javaopencommerce.item.ItemDetailModel;
import com.example.javaopencommerce.item.ItemDetails;
import com.example.javaopencommerce.item.ItemModel;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.List;
import java.util.Objects;

public interface ItemDetailConverter {

    static ItemDetailModel convertToModel(ItemDetails details) {

        List<ImageModel> images = ofNullable(details.getImages()).orElse(emptyList())
                .stream()
                .map(ImageConverter::convertToModel)
                .collect(toList());

        return ItemDetailModel.builder()
                .name(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .additionalImages(images)
                .build();
    }

    static ItemDetailDto convertToDto(ItemModel item, String lang, String defaultLang) {

        ItemDetailModel details = getItemDetailsByLanguage(item, lang, defaultLang);

        List<ImageDto> images = ofNullable(details.getAdditionalImages()).orElse(emptyList())
                .stream()
                .map(ImageConverter::convertToDto)
                .collect(toList());

        return ItemDetailDto.builder()
                .id(item.getId())
                .valueGross(item.getValueGross().asDecimal())
                .vat(item.getVat().asDouble())
                .stock(item.getStock())
                .mainImage(ImageConverter.convertToDto(item.getImage()))
                .name(details.getName())
                .description(details.getDescription())
                .additionalImages(images)
                .build();
    }

    static ItemDetailModel getItemDetailsByLanguage(ItemModel item, String lang, String defaultLang) {
        if (item.getDetails().isEmpty()) {
            return ItemDetailModel.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build();
        }

        return item.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(item.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(ItemDetailModel.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build()));
    }
}
