package com.example.database.repositories.impl;

import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.image.ImageRepository;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

//TODO
@ApplicationScoped
public class ImageRepositoryImpl implements ImageRepository {

    @Override
    public Uni<List<Image>> getImagesByIdList(List<Long> ids) {
        return null;
    }

    @Override
    public Uni<Image> getImageById(Long id) {
        return null;
    }

    @Override
    public Uni<Image> saveImage(Image image) {
        return null;
    }
}
