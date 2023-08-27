package com.example.opencommerce.domain.warehouse;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.OrderId;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ItemStock {

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

    public static ItemStock from(ItemId itemId, Amount stock, Set<ItemReservation> reservationList) {
        return new ItemStock(itemId, stock, reservationList);
    }

    public ItemId id() {
        return this.id;
    }

    public Amount quantityOnHand() {
        return quantityOnHand;
    }

    public List<ItemReservation> reservations() {
        return reservations.stream()
                .toList();
    }

    public OperationResult<ItemStock> increaseStockBy(Amount stockToAdd) {
        this.quantityOnHand = this.quantityOnHand.plus(stockToAdd);
        return OperationResult.success(this);
    }

    public OperationResult<ItemStock> makeStockReservation(ItemReservation reservation) {
        if (freeStock().isLessThan(reservation.reservedAmount())) {
            return OperationResult.failed(
                    String.format("Not enough free items in a stock! Requested: %s, available: %s.",
                            reservation.reservedAmount(),
                            freeStock()));
        }
        this.reservations.add(reservation); // what if there are already reservations for this order and item?
        return OperationResult.success(this);
    }

    public OperationResult<ItemStock> cancelReservationFromOrderWithId(OrderId id) {
        reservations.removeIf(reservation -> reservation.orderId()
                .equals(id));
        return OperationResult.success(this);
    }

    public OperationResult<ItemStock> executeReservationFromOrderWithId(OrderId orderId) {
        Optional<ItemReservation> expectedReservation = reservations.stream()
                .filter(reservation -> reservation.orderId()
                        .equals(orderId))
                .findAny();

        if (expectedReservation.isEmpty()) {
            return OperationResult.failed("Reservation does not exists!");
        }

        ItemReservation reservationToExecute = expectedReservation.get();

        if (this.quantityOnHand.isLessThan(reservationToExecute.reservedAmount())) {
            return OperationResult.failed(
                    String.format("Stock is too low, cant execute! Current stock: %s, Reserved: %s.",
                            this.quantityOnHand,
                            reservationToExecute.reservedAmount()));
        }

        this.quantityOnHand = this.quantityOnHand.minus(reservationToExecute.reservedAmount());
        reservations.remove(reservationToExecute);
        return OperationResult.success(this);
    }

    public Amount freeStock() {
        Amount reserved = reservations.stream()
                .map(ItemReservation::reservedAmount)
                .reduce(Amount.ZERO, Amount::plus);

        return quantityOnHand.minus(reserved);
    }
}
