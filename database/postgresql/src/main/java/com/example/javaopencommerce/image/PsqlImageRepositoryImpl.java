package com.example.javaopencommerce.image;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

//TODO
@ApplicationScoped
class PsqlImageRepositoryImpl implements PsqlImageRepository {

    private final PgPool client;

    PsqlImageRepositoryImpl(PgPool client) {
        this.client = client;
    }

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
