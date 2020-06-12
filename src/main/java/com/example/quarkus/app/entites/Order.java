package com.example.quarkus.app.entites;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Order extends BaseEntity {

    private Address shippingAddress;
    private ShippingMethod shippingMethod;
    private Set<Item> items = new HashSet<>();
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private User user;
}
