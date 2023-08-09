package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.OrderId;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

class ItemReservation {
    private ItemReservationId id;
    private OrderId orderId;
    private Amount reservedAmount;

    private ItemReservation(ItemReservationId id, OrderId orderId, Amount reservedAmount) {
        this.id = requireNonNull(id);
        this.orderId = requireNonNull(orderId);
        this.reservedAmount = requireNonNull(reservedAmount);
    }

    static ItemReservation restore(ItemReservationId id, OrderId orderId, Amount amount) {
        return new ItemReservation(id, orderId, amount);
    }

    static ItemReservation newReservation(OrderId orderId, Amount amount) {
        return new ItemReservation(ItemReservationId.empty(), orderId, amount);
    }

    OrderId orderId() {
        return orderId;
    }

    Amount reservedAmount() {
        return reservedAmount;
    }

    ItemReservationId id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemReservation that = (ItemReservation) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
