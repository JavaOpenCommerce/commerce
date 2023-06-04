package com.example.javaopencommerce.address;


class AddressRepositoryImpl implements AddressRepository {

  private final PsqlAddressRepository addressRepository;

  AddressRepositoryImpl(
      PsqlAddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }
}
