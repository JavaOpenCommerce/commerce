package com.example.opencommerce.adapters.database.order.sql;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import com.example.opencommerce.domain.order.Order;
import com.example.opencommerce.domain.order.Order.OrderItem;
import com.example.opencommerce.domain.order.OrderPrincipal;
import com.example.opencommerce.statics.JsonConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<SimpleProductEntity> items = JsonConverter.convertToObject(simpleProductsJson, ArrayList.class);

        List<OrderItem> orderItems = items.stream()
                .map(this::toOrderItem)
                .toList();

        OrderPrincipal op = new OrderPrincipal(userId, shippingAddressId, paymentMethod);

        return new Order(OrderId.from(id), orderItems, op, paymentStatus,
                status, Value.of(valueGross), Value.of(valueNett), createdAt);
    }

    private OrderItem toOrderItem(SimpleProductEntity productEntity) {
        return new OrderItem(ItemId.of(productEntity.getItemId()), productEntity.getName(),
                Amount.of(productEntity.getAmount()), Value.of(productEntity.getValueGross()),
                Vat.of(productEntity.getVat()));
    }
}
