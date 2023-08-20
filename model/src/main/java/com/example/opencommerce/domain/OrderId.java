package com.example.opencommerce.domain;

import java.util.Objects;
import java.util.UUID;

public class OrderId {

    private final UUID id;

    private OrderId(UUID id) {
        this.id = id;
    }

    public static OrderId from(UUID id) {
        return new OrderId(id);
    }

    public static OrderId random() {
        return new OrderId(UUID.randomUUID());
    }

    public UUID asUUID() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(id, orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
