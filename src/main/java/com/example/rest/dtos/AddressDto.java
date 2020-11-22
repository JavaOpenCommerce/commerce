package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class AddressDto {

    private Long id;
    private String street;
    private String local;
    private String city;
    private String zip;
}
