package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.image.ImageDto;


public interface ImageConverter {

    static ImageDto convertToDto(Image image) {
        return ImageDto.builder()
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
