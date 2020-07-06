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
import static java.util.stream.Collectors.toList;


@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    private final PgPool client;

    public CategoryRepositoryImpl(PgPool client) {this.client = client;}


    @Override
    public Uni<List<Category>> getAll() {
        return client.preparedQuery("SELECT * FROM Category c " +
                "INNER JOIN item_category ic ON ic.category_id = c.id " +
                "INNER JOIN CategoryDetails cd ON cd.category_id = c.id ")
                .onItem().apply(rs -> rowToCategory(rs));
    }

    @Override
    public Uni<List<Category>> getCategoriesByItemId(Long id) {
        return client.preparedQuery("SELECT * FROM Category c " +
                "INNER JOIN item_category ic ON ic.category_id = c.id " +
                "INNER JOIN CategoryDetails cd ON cd.category_id = c.id " +
                "WHERE ic.item_id = $1", Tuple.of(id))
                .onItem().apply(rs -> rowToCategory(rs));
    }

    private List<Category> rowToCategory(RowSet<Row> rs) {
        if (rs == null) return emptyList();

        Map<Long, Category> categories = new HashMap<>();

        for (Row row : rs) {

            Category category = Category.builder()
                    .id(row.getLong("category_id"))
                    .details(new ArrayList<>())
                    .build();

            Long categoryId = category.getId();
            ofNullable(categories.putIfAbsent(categoryId, category))
                    .ifPresentOrElse(
                            cat -> cat
                                    .getDetails()
                                    .add(rowToCategoryDetail(row)),
                            () -> categories
                                    .get(categoryId)
                                    .getDetails()
                                    .add(rowToCategoryDetail(row)));
        }

        return categories.values()
                .stream()
                .collect(toList());
    }

    private CategoryDetails rowToCategoryDetail(Row row) {
        if (row == null) {
            return CategoryDetails.builder().build();
        }

        return CategoryDetails.builder()
                .id(row.getLong(3))
                .name(row.getString("name"))
                .description(row.getString("description"))
                .lang(Locale.forLanguageTag(row.getString("lang")))
                .build();
    }
}
