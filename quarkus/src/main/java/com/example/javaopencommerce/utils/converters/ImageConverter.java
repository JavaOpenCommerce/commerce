package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.image.ImageEntity;


public interface ImageConverter {

    static Image convertToModel(ImageEntity image) {
        return Image.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }

    static ImageDto convertToDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }

    static ImageEntity convertModelToEntity(Image image) {
        return ImageEntity.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }

    static Image convertDtoToModel(ImageDto image) {
        return Image.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }
}
