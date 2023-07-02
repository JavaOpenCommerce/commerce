package com.example.javaopencommerce.order;

import static java.util.Objects.requireNonNull;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import com.example.javaopencommerce.order.Item.ItemSnapshot;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

class Order {

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

    this.orderPrincipal = orderPrincipal;
    this.orderBody = orderBody;
    this.valueGross = valueGross;
    this.valueNett = valueNett;

    validateOrderIntegrity();
  }

  Order(OrderId id, List<OrderItem> orderBody, OrderPrincipal orderPrincipal,
      PaymentStatus paymentStatus, OrderStatus status, Value valueGross, Value valueNett,
      Instant createdAt) {
    this.id = id;
    this.orderBody = orderBody;
    this.orderPrincipal = orderPrincipal;
    this.paymentStatus = paymentStatus;
    this.status = status;
    this.valueGross = valueGross;
    this.valueNett = valueNett;
    this.createdAt = createdAt;

    validateOrderIntegrity();
  }

  static Order newOrder(CardSnapshot card, OrderPrincipal orderPrincipal) {
    requireNonNull(card);
    requireNonNull(orderPrincipal);
    List<OrderItem> orderBody = card.items().stream().map(OrderItem::fromCardItem).toList();
    return new Order(orderBody, card.cardValueGross(), card.cardValueNett(), orderPrincipal);
  }

  OrderSnapshot getSnapshot() {
    return OrderSnapshot.builder().id(id).orderValueNett(valueNett)
        .orderValueGross(valueGross).creationDate(createdAt).orderStatus(status)
        .paymentStatus(paymentStatus).orderPrincipal(orderPrincipal.getSnapshot())
        .orderBody(Collections.unmodifiableList(orderBody)).build();
  }

  private void validateOrderIntegrity() {

    Value calculatedValueGross = this.orderBody.stream()
        .map(item -> item.valueGross.multiply(item.amount.asDecimal()))
        .reduce(Value.ZERO, Value::add);

    if (calculatedValueGross != this.valueGross) {
      throw new IllegalStateException(String.format(
          "Violation of order integrity, gross value not matching, calculated %s, expected: %s",
          calculatedValueGross, this.valueGross));
    }

    Value calculatedValueNett = this.orderBody.stream()
        .map(item -> item.getValueGross().toNett(item.vat).multiply(item.valueGross.getValue()))
        .reduce(Value.ZERO, Value::add);

    if (calculatedValueNett != this.valueNett) {
      throw new IllegalStateException(String.format(
          "Violation of order integrity, nett value not matching, calculated %s, expected: %s",
          calculatedValueNett, this.valueNett));
    }

    // check if shipping method present
  }

  @lombok.Value
  static class OrderItem {

    ItemId itemId;
    String name;
    Amount amount;
    Value valueGross;
    Vat vat;

    static OrderItem fromCardItem(CardItemSnapshot cardItem) {
      requireNonNull(cardItem);
      ItemSnapshot itemSnapshot = cardItem.itemSnapshot();
      return new OrderItem(itemSnapshot.id(), itemSnapshot.name(), cardItem.amount(),
          itemSnapshot.valueGross(), itemSnapshot.vat());
    }

  }
}

