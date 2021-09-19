package com.example.javaopencommerce.address;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class Address {

    Long id;
    String street;
    String local;
    String city;
    String zip;
    Long userId;

}
