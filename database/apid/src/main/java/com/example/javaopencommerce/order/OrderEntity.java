package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.order.Order.OrderItem;
import com.example.javaopencommerce.statics.JsonConverter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
class OrderEntity {

    @Id
    private UUID id;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private String paymentStatus;
    private String paymentMethod;
    private String status;
    private BigDecimal valueGross;
    private BigDecimal valueNett;

    private UUID userId;
    private UUID shippingAddressId;

    private String simpleProductsJson;

    Order toOrderModel() {
        List<SimpleProductEntity> items = JsonConverter.convertToObject(simpleProductsJson,
                new ArrayList<SimpleProductEntity>() {
                }.getClass()
                        .getGenericSuperclass());

        List<OrderItem> orderItems = items.stream()
                .map(this::toOrderItem)
                .collect(toList());

        OrderPrincipal op = new OrderPrincipal(userId, shippingAddressId, paymentMethod);

        return new Order(OrderId.from(id), orderItems, op, PaymentStatus.valueOf(paymentStatus),
                OrderStatus.valueOf(status), Value.of(valueGross), Value.of(valueNett), createdAt);
    }

    private OrderItem toOrderItem(SimpleProductEntity productEntity) {
        return new OrderItem(ItemId.of(productEntity.getItemId()), productEntity.getName(),
                Amount.of(productEntity.getAmount()), Value.of(productEntity.getValueGross()),
                Vat.of(productEntity.getVat()));
    }
}
