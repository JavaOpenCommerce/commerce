package com.example.javaopencommerce.address.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

  private Long id;
  private String street;
  private String local;
  private String city;
  private String zip;
}
