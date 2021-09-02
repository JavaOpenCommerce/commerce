package com.example.database.repositories.impl;

import com.example.database.entity.Image;
import com.example.database.repositories.interfaces.ImageRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

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
