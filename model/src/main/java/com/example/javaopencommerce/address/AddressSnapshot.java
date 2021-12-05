package com.example.javaopencommerce.address;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class AddressSnapshot {

  Long id;
  String street;
  String local;
  String city;
  String zip;

}
