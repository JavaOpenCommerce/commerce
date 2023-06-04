package com.example.javaopencommerce.image;

import com.example.javaopencommerce.image.dtos.ImageDto;
import java.util.List;

public interface ImageQueryRepository {

  List<ImageDto> getImagesByIdList(List<Long> ids);

  ImageDto getImageById(Long id);
}
