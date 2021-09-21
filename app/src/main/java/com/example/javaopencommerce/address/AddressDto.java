package com.example.javaopencommerce.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {

    private Long id;
    private Long userId;
    private String street;
    private String local;
    private String city;
    private String zip;
}
