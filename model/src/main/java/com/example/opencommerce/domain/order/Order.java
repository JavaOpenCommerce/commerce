package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.*;
import com.example.opencommerce.domain.order.Item.ItemSnapshot;
import com.example.opencommerce.domain.order.exceptions.ordervalidation.OrderValueNotMatchingValidationException;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Order {

    private final OrderId id;
    private final List<OrderItem> orderBody;
    private final OrderPrincipal orderPrincipal;
    private final Value valueGross;
    private final Value valueNett;
    private final Instant createdAt;
    private PaymentStatus paymentStatus;
    private OrderStatus status;

    private Order(List<OrderItem> orderBody, Value valueGross, Value valueNett,
                  OrderPrincipal orderPrincipal) {
        this.id = OrderId.random();
        this.status = OrderStatus.NEW;
        this.paymentStatus = PaymentStatus.BEFORE_PAYMENT;
        this.createdAt = Instant.now();

        this.orderPrincipal = requireNonNull(orderPrincipal);
        this.orderBody = requireNonNull(orderBody);
        this.valueGross = requireNonNull(valueGross);
        this.valueNett = requireNonNull(valueNett);

        validateOrderIntegrity();
    }

    public Order(OrderId id, List<OrderItem> orderBody, OrderPrincipal orderPrincipal,
                 String paymentStatus, String status, Value valueGross, Value valueNett,
                 Instant createdAt) {
        requireNonNull(id);
        requireNonNull(orderBody);
        requireNonNull(orderPrincipal);
        requireNonNull(paymentStatus);
        requireNonNull(status);
        requireNonNull(valueGross);
        requireNonNull(valueNett);
        requireNonNull(createdAt);

        this.id = id;
        this.orderBody = orderBody;
        this.orderPrincipal = orderPrincipal;
        this.paymentStatus = PaymentStatus.valueOf(paymentStatus);
        this.status = OrderStatus.valueOf(status);
        this.valueGross = valueGross;
        this.valueNett = valueNett;
        this.createdAt = createdAt;

        validateOrderIntegrity();
    }

    static Order newOrder(CardSnapshot card, OrderPrincipal orderPrincipal) {
        requireNonNull(card);
        requireNonNull(orderPrincipal);
        List<OrderItem> orderBody = card.items()
                .stream()
                .filter(item -> !item.amount()
                        .isZero())
                .map(OrderItem::fromCardItem)
                .toList();
        return new Order(orderBody, card.cardValueGross(), card.cardValueNett(), orderPrincipal);
    }

    public OrderSnapshot getSnapshot() {
        return OrderSnapshot.builder()
                .id(id)
                .orderValueNett(valueNett)
                .orderValueGross(valueGross)
                .creationDate(createdAt)
                .orderStatus(status.name())
                .paymentStatus(paymentStatus.name())
                .orderPrincipal(orderPrincipal.getSnapshot())
                .orderBody(Collections.unmodifiableList(orderBody))
                .build();
    }

    private void validateOrderIntegrity() {

        Value calculatedValueGross = this.orderBody.stream()
                .map(item -> item.valueGross.multiply(item.amount.asDecimal()))
                .reduce(Value.ZERO, Value::add);

        if (!calculatedValueGross.equals(this.valueGross)) {
            throw new OrderValueNotMatchingValidationException("gross", calculatedValueGross, this.valueGross);
        }

        Value calculatedValueNett = this.orderBody.stream()
                .map(item -> item.getValueGross()
                        .multiply(item.amount.asDecimal())
                        .toNett(item.vat))
                .reduce(Value.ZERO, Value::add);

        if (!calculatedValueNett.equals(this.valueNett)) {
            throw new OrderValueNotMatchingValidationException("nett", calculatedValueNett, this.valueNett);
        }
    }

    @lombok.Value
    public static class OrderItem {

        ItemId id;
        String name;
        Amount amount;
        Value valueGross;
        Vat vat;

        static OrderItem fromCardItem(CardItem.CardItemSnapshot cardItem) {
            requireNonNull(cardItem);
            ItemSnapshot itemSnapshot = cardItem.itemSnapshot();
            return new OrderItem(itemSnapshot.id(), itemSnapshot.name(), cardItem.amount(),
                    itemSnapshot.valueGross(), itemSnapshot.vat());
        }
    }
}

