package com.example.javaopencommerce.image;

import static java.lang.String.format;

import com.example.javaopencommerce.CommonRow;
import com.example.javaopencommerce.exception.EntityNotFoundException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

//TODO
@ApplicationScoped
class PsqlImageRepositoryImpl implements PsqlImageRepository {

    private final PgPool client;
    private final ImageMapper mapper;

    private static final String SELECT_IMAGE_BASE = "SELECT * FROM IMAGE i";

    PsqlImageRepositoryImpl(PgPool client) {
        this.client = client;
        this.mapper = new ImageMapper();
    }

    @Override
    public Uni<List<ImageEntity>> getImagesByIdList(List<Long> ids) {
        return client.preparedQuery(format("%s WHERE id = ANY ($1)", SELECT_IMAGE_BASE))
            .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
            .map(this.mapper::getImages);
    }

    @Override
    public Uni<ImageEntity> getImageById(Long id) {
        return client.preparedQuery(format("%s WHERE id = $1", SELECT_IMAGE_BASE))
            .execute(Tuple.of(id))
            .map(rs -> {
                if (CommonRow.isRowSetEmpty(rs)) {
                    throw new EntityNotFoundException("Image", id);
                }
                return this.mapper.rowToImage(rs.iterator().next());
            });
    }

    @Override
    public Uni<ImageEntity> saveImage(ImageEntity image) {
        return null;
    }
}
