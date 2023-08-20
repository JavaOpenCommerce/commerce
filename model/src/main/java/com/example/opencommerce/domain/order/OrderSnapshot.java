package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.order.OrderPrincipal.OrderPrincipalSnapshot;
import lombok.Builder;

import java.time.Instant;
import java.util.List;


@Builder
@lombok.Value
public class OrderSnapshot {

    OrderId id;
    List<Order.OrderItem> orderBody;
    OrderPrincipalSnapshot orderPrincipal;

    String paymentStatus;
    String orderStatus;

    Value orderValueGross;
    Value orderValueNett;
    Instant creationDate;
}
