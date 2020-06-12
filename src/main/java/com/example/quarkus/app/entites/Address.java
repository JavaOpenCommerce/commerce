package com.example.quarkus.app.entites;

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
