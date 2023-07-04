package com.example.javaopencommerce.order;

import java.util.UUID;

class OrderId {

    private final UUID id;

    private OrderId(UUID id) {
        this.id = id;
    }

    static OrderId from(UUID id) {
        return new OrderId(id);
    }

    static OrderId random() {
        return new OrderId(UUID.randomUUID());
    }

    public UUID asUUID() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
