package com.example.javaopencommerce.image;

import com.example.javaopencommerce.image.dtos.ImageDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ImageQueryRepository {

  Uni<List<ImageDto>> getImagesByIdList(List<Long> ids);
  Uni<ImageDto> getImageById(Long id);
}
