package com.example.javaopencommerce.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressEntity {

    private Long id;
    private String street;
    private String local;
    private String city;
    private String zip;
    private Long userId;
}
