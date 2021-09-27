package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.order.OrderDetails.SimpleProduct;
import com.example.javaopencommerce.statics.JsonConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderDetailsEntity {

    private Long id;
    private LocalDate creationDate;

    @Builder.Default
    private Long shippingAddressId = 1L;
    private String paymentStatus;
    private String paymentMethod;
    private String orderStatus;
    private BigDecimal valueGross;
    private BigDecimal valueNett;

    @Builder.Default
    private Long userEntityId = 1L;

    @Builder.Default
    private final String simpleProductsJson = "{}";

    OrderDetails toOrderDetailsModel() {
        List<SimpleProductEntity> products = JsonConverter.convertToObject(simpleProductsJson,
            new ArrayList<SimpleProductEntity>(){}.getClass().getGenericSuperclass());

        return OrderDetails.builder()
            .id(id)
            .creationDate(creationDate)
            .orderValueGross(Value.of(valueGross))
            .orderValueNett(Value.of(valueNett))
            .paymentMethod(PaymentMethod.valueOf(paymentMethod))
            .paymentStatus(PaymentStatus.valueOf(paymentStatus))
            .orderStatus(OrderStatus.valueOf(orderStatus))
            .orderBody(products.stream()
                .map(this::toSimpleProduct)
                .collect(toList()))
            .build();
    }

    SimpleProduct toSimpleProduct(SimpleProductEntity productEntity) {
        return SimpleProduct.builder()
            .itemId(productEntity.getItemId())
            .name(productEntity.getName())
            .vat(Vat.of(productEntity.getVat()))
            .amount(Amount.of(productEntity.getAmount()))
            .valueGross(Value.of(productEntity.getValueGross()))
            .build();
    }
}
