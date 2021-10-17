package com.example.javaopencommerce.image;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.javaopencommerce.image.dtos.ImageDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

class ImageQueryRepositoryImpl implements ImageQueryRepository {

  private final PsqlImageRepository imageRepository;

  ImageQueryRepositoryImpl(PsqlImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public Uni<List<ImageDto>> getImagesByIdList(List<Long> ids) {
    return imageRepository.getImagesByIdList(ids).onItem()
        .transform(images -> images.stream()
            .map(ImageEntity::toImageModel)
            .map(Image::getSnapshot)
            .map(ImageDtoFactory::fromSnapshot)
            .collect(toUnmodifiableList()));
  }

  @Override
  public Uni<ImageDto> getImageById(Long id) {
    return imageRepository.getImageById(id).onItem()
        .transform(image ->
            ImageDtoFactory.fromSnapshot(
                image.toImageModel().getSnapshot()));
  }
}
