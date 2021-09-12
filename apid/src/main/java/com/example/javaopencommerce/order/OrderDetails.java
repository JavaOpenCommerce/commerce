package com.example.javaopencommerce.order;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetails {

    private Long id;
    private LocalDate creationDate;
    private Long shippingAddressId;

    @Builder.Default
    private final PaymentStatus paymentStatus = PaymentStatus.BEFORE_PAYMENT;

    @Builder.Default
    private final PaymentMethod paymentMethod = PaymentMethod.MONEY_TRANSFER;

    @Builder.Default
    private final OrderStatus orderStatus = OrderStatus.NEW;

    private Long userEntityId;

    @Builder.Default
    private final String productsJson = "{}";
}
