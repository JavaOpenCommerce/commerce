package com.example.javaopencommerce.image;

import com.example.javaopencommerce.image.dtos.ImageDto;

class ImageDtoFactory {

  //TODO remove static when removing ProducerConverter
  public static ImageDto fromSnapshot(ImageSnapshot imageSnapshot) {
    return ImageDto.builder()
        .id(imageSnapshot.getId())
        .alt(imageSnapshot.getAlt())
        .url(imageSnapshot.getUrl())
        .build();
  }
}
