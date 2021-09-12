package com.example.utils.converters;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.image.ImageModel;
import com.example.javaopencommerce.image.Image;


public interface ImageConverter {

    static ImageModel convertToModel(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }

    static ImageDto convertToDto(ImageModel image) {
        return ImageDto.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }

    static Image convertModelToEntity(ImageModel image) {
        return Image.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }

    static ImageModel convertDtoToModel(ImageDto image) {
        return ImageModel.builder()
                .id(image.getId())
                .alt(image.getAlt())
                .url(image.getUrl())
                .build();
    }
}
