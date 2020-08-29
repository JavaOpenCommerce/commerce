package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.example.database.entity.OrderStatus.NEW;
import static com.example.database.entity.PaymentStatus.BEFORE_PAYMENT;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetails {

    private Long id;
    private LocalDate creationDate;
    private Address shippingAddress;

    @Builder.Default
    private PaymentStatus paymentStatus = BEFORE_PAYMENT;

    private PaymentMethod paymentMethod;

    @Builder.Default
    private OrderStatus orderStatus = NEW;

    private UserEntity userEntity;

    private List<Product> products;
}
