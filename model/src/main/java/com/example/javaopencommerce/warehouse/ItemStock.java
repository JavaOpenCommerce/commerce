package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.OperationResult;
import com.example.javaopencommerce.OrderId;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.javaopencommerce.OperationResult.*;
import static java.util.Objects.requireNonNull;

class ItemStock {

    private final Set<ItemReservation> reservations = new HashSet<>();
    private final ItemId id;
    private Amount quantityOnHand;

    private ItemStock(ItemId id, Amount stock) {
        this.id = requireNonNull(id);
        this.quantityOnHand = requireNonNull(stock);
    }

    private ItemStock(ItemId id, Amount stock, Set<ItemReservation> reservationList) {
        this.id = requireNonNull(id);
        this.quantityOnHand = requireNonNull(stock);
        this.reservations.addAll(requireNonNull(reservationList));
    }

    static ItemStock empty(ItemId itemId) {
        return new ItemStock(itemId, Amount.ZERO);
    }

    static ItemStock from(ItemId itemId, Amount stock, Set<ItemReservation> reservationList) {
        return new ItemStock(itemId, stock, reservationList);
    }

    ItemId id() {
        return this.id;
    }

    Amount quantityOnHand() {
        return quantityOnHand;
    }

    List<ItemReservation> reservations() {
        return reservations.stream()
                .toList();
    }

    OperationResult increaseStockBy(Amount stockToAdd) {
        this.quantityOnHand = this.quantityOnHand.plus(stockToAdd);
        return OK;
    }

    OperationResult makeStockReservation(ItemReservation reservation) {
        if (freeStock().isLessThan(reservation.reservedAmount())) {
            return failed(
                    String.format("Not enough free items in a stock! Requested: %s, available: %s.",
                            reservation.reservedAmount(),
                            freeStock()));
        }
        this.reservations.add(reservation); // what if there are already reservations for this order and item?
        return OK;
    }

    OperationResult cancelReservationFromOrderWithId(OrderId id) {
        reservations.removeIf(reservation -> reservation.orderId()
                .equals(id));
        return OK;
    }

    OperationResult executeReservationFromOrderWithId(OrderId orderId) {
        Optional<ItemReservation> expectedReservation = reservations.stream()
                .filter(reservation -> reservation.orderId()
                        .equals(orderId))
                .findAny();

        if (expectedReservation.isEmpty()) {
            return FAILED;
        }

        ItemReservation reservationToExecute = expectedReservation.get();

        if (this.quantityOnHand.isLessThan(reservationToExecute.reservedAmount())) {
            return failed(
                    String.format("Stock is too low, cant execute! Current stock: %s, Reserved: %s.",
                            this.quantityOnHand,
                            reservationToExecute.reservedAmount()));
        }

        this.quantityOnHand = this.quantityOnHand.minus(reservationToExecute.reservedAmount());
        reservations.remove(reservationToExecute);
        return OK;
    }

    Amount freeStock() {
        Amount reserved = reservations.stream()
                .map(ItemReservation::reservedAmount)
                .reduce(Amount.ZERO, Amount::plus);

        return quantityOnHand.minus(reserved);
    }
}
