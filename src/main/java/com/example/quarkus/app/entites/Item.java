package com.example.quarkus.app.entites;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Item extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
}
