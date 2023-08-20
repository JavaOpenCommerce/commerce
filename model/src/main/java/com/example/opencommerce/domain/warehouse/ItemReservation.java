package com.example.opencommerce.domain.warehouse;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.OrderId;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ItemReservation {
    private ItemReservationId id;
    private OrderId orderId;
    private Amount reservedAmount;

    private ItemReservation(ItemReservationId id, OrderId orderId, Amount reservedAmount) {
        this.id = requireNonNull(id);
        this.orderId = requireNonNull(orderId);
        this.reservedAmount = requireNonNull(reservedAmount);
    }

    public static ItemReservation restore(ItemReservationId id, OrderId orderId, Amount amount) {
        return new ItemReservation(id, orderId, amount);
    }

    public static ItemReservation newReservation(OrderId orderId, Amount amount) {
        return new ItemReservation(ItemReservationId.empty(), orderId, amount);
    }

    public OrderId orderId() {
        return orderId;
    }

    public Amount reservedAmount() {
        return reservedAmount;
    }

    public ItemReservationId id() {
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
