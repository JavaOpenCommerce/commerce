package com.example.javaopencommerce.category;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;


class PsqlCategoryRepositoryImpl implements PsqlCategoryRepository {

    private final PgPool client;
    private final CategoryMapper categoryMapper;

    public PsqlCategoryRepositoryImpl(PgPool client) {
        this.client = client;
        this.categoryMapper = new CategoryMapper();
    }


    @Override
    public Uni<List<CategoryEntity>> getAll() {
        return this.client.preparedQuery("SELECT * FROM category c " +
                        "INNER JOIN category_details cd ON cd.category_id = c.id ")
                .execute()
                .map(this.categoryMapper::rowToCategoryList);
    }

    @Override
    public Uni<List<CategoryEntity>> getCategoriesByItemId(Long id) {
        return this.client.preparedQuery("SELECT * FROM category c " +
                        "INNER JOIN category_details cd ON cd.category_id = c.id " +
                        "INNER JOIN item_category ic ON ic.category_id = c.id " +
                        "WHERE ic.item_id = $1")
                .execute(Tuple.of(id))
                .map(this.categoryMapper::rowToCategoryList);
    }

    @Override
    public Uni<List<CategoryEntity>> getCategoriesListByIdList(List<Long> ids) {
        return this.client.preparedQuery("SELECT * FROM category c " +
                        "INNER JOIN category_details cd ON cd.category_id = c.id " +
                        "INNER JOIN item_category ic ON ic.category_id = c.id " +
                        "WHERE ic.item_id = ANY ($1)")
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this.categoryMapper::rowToCategoryList);
    }
}
