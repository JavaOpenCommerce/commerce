package com.example.javaopencommerce.catalog;

import java.util.List;
import java.util.UUID;

public class CategoryEnricher {

    private final Category catalog;

    public CategoryEnricher(Category catalog) {
        this.catalog = catalog;
    }

    public List<UUID> findAllCategoryIdsForId(UUID categoryId) {
        return catalog.findAllSubcategoryIdsFor(CategoryId.of(categoryId))
                .stream()
                .map(CategoryId::id)
                .toList();
    }
}
