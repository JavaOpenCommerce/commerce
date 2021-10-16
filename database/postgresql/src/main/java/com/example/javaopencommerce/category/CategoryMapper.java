package com.example.javaopencommerce.category;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.CommonRow;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class CategoryMapper {

    private static final String ID = "id";
    private static final String CATEGORY_ID = "category_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String LANG = "lang";

    public List<CategoryEntity> rowToCategoryList(RowSet<Row> rs) {
        if (CommonRow.isRowSetEmpty(rs)) {
            return emptyList();
        }

        Map<Long, CategoryEntity> categories = new HashMap<>();
        for (Row row : rs) {
            CategoryEntity category = CategoryEntity.builder()
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

    public CategoryDetailsEntity rowToCategoryDetail(Row row) {
        if (row == null) {
            return CategoryDetailsEntity.builder().build();
        }
        return CategoryDetailsEntity.builder()
                .id(row.getLong(1))
                .name(row.getString(NAME))
                .description(row.getString(DESCRIPTION))
                .lang(Locale.forLanguageTag(row.getString(LANG)))
                .build();
    }
}
