package com.example.javaopencommerce.category;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


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

  public Uni<List<Long>> getCategoryIdsForItem(Long itemId) {
    return this.client.preparedQuery("SELECT category_id FROM ITEM_CATEGORY WHERE item_id = $1")
        .execute(Tuple.of(itemId))
        .map(rows ->
            StreamSupport
                .stream(rows.spliterator(), false)
                .map(row -> row.getLong("category_id"))
                .collect(Collectors.toList()));
  }

  @Override
  public Uni<List<CategoryEntity>> getCategoriesForItem(Long id) {
    return this.client.preparedQuery("SELECT * FROM category c "
            + "LEFT JOIN category_details cd ON cd.category_id = c.id "
            + "LEFT JOIN item_category ic on ic.category_id = c.id "
            + "WHERE ic.item_id = $1")
        .execute(Tuple.of(id))
        .map(this.categoryMapper::rowToCategoryList);
  }

  @Override
  public Uni<List<CategoryEntity>> getCategoriesForItems(List<Long> ids) {
    return this.client.preparedQuery("SELECT * FROM category c " +
            "INNER JOIN category_details cd ON cd.category_id = c.id " +
            "INNER JOIN item_category ic ON ic.category_id = c.id " +
            "WHERE ic.item_id = ANY ($1)")
        .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
        .map(this.categoryMapper::rowToCategoryList);
  }
}
