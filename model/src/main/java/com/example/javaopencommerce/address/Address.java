package com.example.javaopencommerce.address;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public
class Address {

    private Long id;
    private String street;
    private String local;
    private String city;
    private String zip;
    private Long userId;
}
