package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static com.example.database.entity.OrderStatus.NEW;
import static com.example.database.entity.PaymentStatus.BEFORE_PAYMENT;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDetails extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address shippingAddress;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = BEFORE_PAYMENT;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = NEW;

    @ManyToOne
    private User user;
}
