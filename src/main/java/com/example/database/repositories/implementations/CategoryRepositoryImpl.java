package com.example.database.repositories.implementations;

import com.example.database.entity.Category;
import com.example.database.entity.CategoryDetails;
import com.example.database.repositories.interfaces.CategoryRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;


@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    private final PgPool client;

    public CategoryRepositoryImpl(PgPool client) {this.client = client;}


    @Override
    public Uni<List<Category>> getAll() {
        return client.preparedQuery("SELECT * FROM Category c " +
                                        "INNER JOIN categorydetails cd ON cd.category_id = c.id ")
                .onItem().apply(rs -> rowToCategoryList(rs));
    }

    @Override
    public Uni<List<Category>> getCategoriesByItemId(Long id) {
        return client.preparedQuery("SELECT * FROM Category c " +
                                        "INNER JOIN categorydetails cd ON cd.category_id = c.id " +
                                        "INNER JOIN item_category ic ON ic.category_id = c.id " +
                                        "WHERE ic.item_id = $1", Tuple.of(id))
                .onItem().apply(rs -> rowToCategoryList(rs));
    }

    @Override
    public Uni<List<Category>> getCategoriesListByIdList(List<Long> ids) {
        return client.preparedQuery("SELECT * FROM Category c " +
                                        "INNER JOIN categorydetails cd ON cd.category_id = c.id " +
                                        "INNER JOIN item_category ic ON ic.category_id = c.id " +
                                        "WHERE ic.item_id = ANY ($1)", Tuple.of(ids.toArray(new Long[ids.size()])))
                .onItem().apply(rs -> rowToCategoryList(rs));
    }

    private List<Category> rowToCategoryList(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        Map<Long, Category> categories = new HashMap<>();
        for (Row row : rs) {
            Category category = Category.builder()
                    .id(row.getLong("category_id"))
                    .build();

            ofNullable(categories.putIfAbsent(category.getId(), category))
                    .ifPresentOrElse(
                            cat -> cat
                                    .getDetails()
                                    .add(rowToCategoryDetail(row)),
                            () -> categories
                                    .get(category.getId())
                                    .getDetails()
                                    .add(rowToCategoryDetail(row)));
        }

        return new ArrayList<>(categories.values());
    }

    private CategoryDetails rowToCategoryDetail(Row row) {
        if (row == null) {
            return CategoryDetails.builder().build();
        }
        return CategoryDetails.builder()
                .id(row.getLong(1))
                .name(row.getString("name"))
                .description(row.getString("description"))
                .lang(Locale.forLanguageTag(row.getString("lang")))
                .build();
    }
}
