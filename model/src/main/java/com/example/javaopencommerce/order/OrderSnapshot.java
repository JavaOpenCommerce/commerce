package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.Order.OrderItem;
import com.example.javaopencommerce.order.OrderPrincipal.OrderPrincipalSnapshot;
import java.time.Instant;
import java.util.List;
import lombok.Builder;


@Builder
@lombok.Value
class OrderSnapshot {

  OrderId id;
  List<OrderItem> orderBody;
  OrderPrincipalSnapshot orderPrincipal;

  PaymentStatus paymentStatus;
  OrderStatus orderStatus;

  Value orderValueGross;
  Value orderValueNett;
  Instant creationDate;
}
