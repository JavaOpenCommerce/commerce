package com.example.database.repositories.impl;

import com.example.database.repositories.impl.mappers.CategoryMapper;
import com.example.javaopencommerce.category.Category;
import com.example.javaopencommerce.category.CategoryRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    private final PgPool client;
    private final CategoryMapper categoryMapper;

    public CategoryRepositoryImpl(PgPool client, CategoryMapper categoryMapper) {
        this.client = client;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public Uni<List<Category>> getAll() {
        return this.client.preparedQuery("SELECT * FROM category c " +
                        "INNER JOIN category_details cd ON cd.category_id = c.id ")
                .execute()
                .map(this.categoryMapper::rowToCategoryList);
    }

    @Override
    public Uni<List<Category>> getCategoriesByItemId(Long id) {
        return this.client.preparedQuery("SELECT * FROM category c " +
                        "INNER JOIN category_details cd ON cd.category_id = c.id " +
                        "INNER JOIN item_category ic ON ic.category_id = c.id " +
                        "WHERE ic.item_id = $1")
                .execute(Tuple.of(id))
                .map(this.categoryMapper::rowToCategoryList);
    }

    @Override
    public Uni<List<Category>> getCategoriesListByIdList(List<Long> ids) {
        return this.client.preparedQuery("SELECT * FROM category c " +
                        "INNER JOIN category_details cd ON cd.category_id = c.id " +
                        "INNER JOIN item_category ic ON ic.category_id = c.id " +
                        "WHERE ic.item_id = ANY ($1)")
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this.categoryMapper::rowToCategoryList);
    }
}
