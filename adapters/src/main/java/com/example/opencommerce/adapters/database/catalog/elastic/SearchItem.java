package com.example.opencommerce.adapters.database.catalog.elastic;

import java.util.Set;

public record SearchItem(Long id, String name, String description, double valueGross, Long producerId,
                         Set<String> categoryIds) {

    public void addAdditionalCategoryIds(Set<String> categoryIds) {
        this.categoryIds.addAll(categoryIds);
    }
}
