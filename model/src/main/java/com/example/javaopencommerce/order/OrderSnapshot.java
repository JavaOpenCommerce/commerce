package com.example.javaopencommerce.order;

import com.example.javaopencommerce.OrderId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.Order.OrderItem;
import com.example.javaopencommerce.order.OrderPrincipal.OrderPrincipalSnapshot;
import lombok.Builder;

import java.time.Instant;
import java.util.List;


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
