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
class AddressEntity {

  private Long id;
  private String street;
  private String local;
  private String city;
  private String zip;

  Address toAddressModel() {
    return Address.builder()
        .id(id)
        .street(street)
        .local(local)
        .city(city)
        .zip(zip)
        .build();
  }
}
