package com.example.javaopencommerce.image;

import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

//TODO
@ApplicationScoped
public class ImageRepositoryImpl implements ImageRepository {

    @Override
    public Uni<List<ImageEntity>> getImagesByIdList(List<Long> ids) {
        return null;
    }

    @Override
    public Uni<ImageEntity> getImageById(Long id) {
        return null;
    }

    @Override
    public Uni<ImageEntity> saveImage(ImageEntity image) {
        return null;
    }
}
