package com.example.javaopencommerce.image;


class ImageRepositoryImpl implements ImageRepository {

  private final PsqlImageRepository imageRepository;

  ImageRepositoryImpl(PsqlImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public Image saveImage(Image image) {
    return imageRepository.saveImage(ImageEntity.fromSnapshot(image.getSnapshot()))
        .map(ImageEntity::toImageModel).await().indefinitely();
  }
}
