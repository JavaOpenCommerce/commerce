package com.example.database.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ShippingMethod extends BaseEntity {

    private String name;
    private BigDecimal price;
    private int waitPeriod;
}
