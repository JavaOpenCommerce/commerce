package com.example.javaopencommerce.address;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.address.dtos.AddressDto;
import java.util.List;

class AddressQueryRepositoryImpl implements AddressQueryRepository {

  private final PsqlAddressRepository addressRepository;

  AddressQueryRepositoryImpl(
      PsqlAddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public List<AddressDto> findByZip(String zip) {
    return addressRepository.findByZip(zip)
        .map(addresses ->
            addresses.stream()
                .map(AddressEntity::toAddressModel)
                .map(this::toDto)
                .collect(toList())).await().indefinitely();
  }

  @Override
  public AddressDto findById(Long id) {
    return addressRepository.findById(id)
        .map(AddressEntity::toAddressModel)
        .map(this::toDto).await().indefinitely();
  }

  @Override
  public List<AddressDto> getUserAddressListById(Long id) {
    return addressRepository.getUserAddressListById(id)
        .map(addresses ->
            addresses.stream()
                .map(AddressEntity::toAddressModel)
                .map(this::toDto)
                .collect(toList())).await().indefinitely();
  }

  AddressDto toDto(Address address) {
    AddressSnapshot snapshot = address.getSnapshot();
    return AddressDto.builder()
        .id(snapshot.getId())
        .local(snapshot.getLocal())
        .city(snapshot.getCity())
        .street(snapshot.getStreet())
        .zip(snapshot.getZip())
        .build();
  }
}
