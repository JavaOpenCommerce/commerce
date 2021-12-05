package com.example.javaopencommerce.address;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class Address {

  static Address restore(AddressSnapshot addressSnapshot) {
    return Address.builder()
        .id(addressSnapshot.getId())
        .street(addressSnapshot.getStreet())
        .local(addressSnapshot.getLocal())
        .city(addressSnapshot.getCity())
        .zip(addressSnapshot.getZip())
        .build();
  }

  Long id;
  String street;
  String local;
  String city;
  String zip;

  AddressSnapshot getSnapshot() {
    return AddressSnapshot.builder()
        .id(id)
        .street(street)
        .local(local)
        .city(city)
        .zip(zip)
        .build();
  }

}
