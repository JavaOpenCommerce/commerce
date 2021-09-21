package com.example.javaopencommerce.image;

import static java.util.stream.Collectors.toUnmodifiableList;

import io.smallrye.mutiny.Uni;
import java.util.List;

class ImageRepositoryImpl implements ImageRepository {

  private final PsqlImageRepository imageRepository;

  ImageRepositoryImpl(PsqlImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public Uni<List<Image>> getImagesByIdList(List<Long> ids) {
    return imageRepository.getImagesByIdList(ids)
        .map(images ->
            images.stream()
                .map(ImageEntity::toImageModel)
                .collect(toUnmodifiableList())
        );
  }

  @Override
  public Uni<Image> getImageById(Long id) {
    return imageRepository.getImageById(id)
        .map(ImageEntity::toImageModel);
  }

  @Override
  public Uni<Image> saveImage(Image image) {
    return imageRepository.saveImage(ImageEntity.fromSnapshot(image.getSnapshot()))
        .map(ImageEntity::toImageModel);
  }
}
