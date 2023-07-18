package com.example.javaopencommerce;

import java.util.Objects;

public class ItemId {

    private final Long id;

    private ItemId(Long id) {
        this.id = id;
    }

    public static ItemId of(Long id) {
        return new ItemId(id);
    }

    public Long asLong() {
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
        ItemId itemId = (ItemId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
