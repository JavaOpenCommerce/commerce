package com.example.utils.converters;

import com.example.business.models.ImageModel;
import com.example.database.entity.Image;
import com.example.rest.dtos.ImageDto;

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
}
