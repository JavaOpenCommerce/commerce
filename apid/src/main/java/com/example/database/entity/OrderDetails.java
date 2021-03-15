package com.example.database.entity;

import lombok.*;

import java.time.LocalDate;

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
    private PaymentStatus paymentStatus = PaymentStatus.BEFORE_PAYMENT;

    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.MONEY_TRANSFER;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NEW;

    private Long userEntityId;

    @Builder.Default
    private String productsJson = "{}";
}
