package com.example.javaopencommerce.image;

import io.smallrye.mutiny.Uni;

class ImageRepositoryImpl implements ImageRepository {

  private final PsqlImageRepository imageRepository;

  ImageRepositoryImpl(PsqlImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public Uni<Image> saveImage(Image image) {
    return imageRepository.saveImage(ImageEntity.fromSnapshot(image.getSnapshot()))
        .map(ImageEntity::toImageModel);
  }
}
