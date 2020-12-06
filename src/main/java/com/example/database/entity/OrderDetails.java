package com.example.database.entity;

import lombok.*;

import java.time.LocalDate;

import static com.example.database.entity.OrderStatus.NEW;
import static com.example.database.entity.PaymentMethod.MONEY_TRANSFER;
import static com.example.database.entity.PaymentStatus.BEFORE_PAYMENT;

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
    private PaymentStatus paymentStatus = BEFORE_PAYMENT;

    @Builder.Default
    private PaymentMethod paymentMethod = MONEY_TRANSFER;

    @Builder.Default
    private OrderStatus orderStatus = NEW;

    private Long userEntityId;

    @Builder.Default
    private String productsJson = "{}";
}
