package com.example.javaopencommerce.image;

import com.example.javaopencommerce.image.dtos.ImageDto;
import java.util.List;

class ImageQueryRepositoryImpl implements ImageQueryRepository {

  private final PsqlImageRepository imageRepository;

  ImageQueryRepositoryImpl(PsqlImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public List<ImageDto> getImagesByIdList(List<Long> ids) {
    return imageRepository.getImagesByIdList(ids).onItem().transform(
        images -> images.stream().map(ImageEntity::toImageModel).map(Image::getSnapshot)
            .map(ImageDtoFactory::fromSnapshot).toList()).await().indefinitely();
  }

  @Override
  public ImageDto getImageById(Long id) {
    return imageRepository.getImageById(id).onItem()
        .transform(image -> ImageDtoFactory.fromSnapshot(image.toImageModel().getSnapshot()))
        .await().indefinitely();
  }
}
