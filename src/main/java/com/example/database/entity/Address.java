package com.example.database.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address extends BaseEntity {

    private Long id;
    private String street;
    private String local;
    private String zip;
}
