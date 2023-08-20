package com.example.opencommerce.app.catalog;

import com.example.opencommerce.domain.catalog.Category;
import com.example.opencommerce.domain.catalog.CategoryId;

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
