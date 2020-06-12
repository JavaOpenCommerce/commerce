package com.example.quarkus.app.entites;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order extends BaseEntity {

    private Address shippingAddress;
    private ShippingMethod shippingMethod;
}
