package com.example.database.repositories.impl.mappers;

import com.example.database.entity.Category;
import com.example.database.entity.CategoryDetails;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

import static com.example.utils.CommonRow.isRowSetEmpty;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@ApplicationScoped
public class CategoryMapper {

    private static final String ID = "id";
    private static final String CATEGORY_ID = "category_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String LANG = "lang";

    public List<Category> rowToCategoryList(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return emptyList();
        }

        Map<Long, Category> categories = new HashMap<>();
        for (Row row : rs) {
            Category category = Category.builder()
                    .id(row.getLong(CATEGORY_ID))
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

    public CategoryDetails rowToCategoryDetail(Row row) {
        if (row == null) {
            return CategoryDetails.builder().build();
        }
        return CategoryDetails.builder()
                .id(row.getLong(1))
                .name(row.getString(NAME))
                .description(row.getString(DESCRIPTION))
                .lang(Locale.forLanguageTag(row.getString(LANG)))
                .build();
    }
}
