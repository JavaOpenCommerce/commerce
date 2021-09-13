package com.example.javaopencommerce.image;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ImageRepository {

    Uni<List<Image>> getImagesByIdList(List<Long> ids);
    Uni<Image> getImageById(Long id);
    Uni<Image> saveImage(Image image);

}
