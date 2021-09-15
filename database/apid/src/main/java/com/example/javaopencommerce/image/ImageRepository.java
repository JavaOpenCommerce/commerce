package com.example.javaopencommerce.image;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ImageRepository {

    Uni<List<ImageEntity>> getImagesByIdList(List<Long> ids);
    Uni<ImageEntity> getImageById(Long id);
    Uni<ImageEntity> saveImage(ImageEntity image);

}
