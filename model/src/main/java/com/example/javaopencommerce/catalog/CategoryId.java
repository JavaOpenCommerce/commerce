package com.example.javaopencommerce.catalog;

import java.util.Objects;
import java.util.UUID;

public class CategoryId {

    private final UUID id;

    private CategoryId(UUID id) {
        this.id = id;
    }

    static CategoryId of(UUID id) {
        return new CategoryId(id);
    }

    static CategoryId random() {
        return new CategoryId(UUID.randomUUID());
    }

    public UUID id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryId categoryId = (CategoryId) o;
        return Objects.equals(id, categoryId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
