package com.example.javaopencommerce.catalog;

import java.util.Set;

record SearchItem(Long id, String name, String description, double valueGross, Long producerId,
                  Set<String> categoryIds) {

    void addAdditionalCategoryIds(Set<String> categoryIds) {
        this.categoryIds.addAll(categoryIds);
    }
}
