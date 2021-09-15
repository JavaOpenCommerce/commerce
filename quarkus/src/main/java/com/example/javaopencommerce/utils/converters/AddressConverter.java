package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.address.Address;
import com.example.javaopencommerce.address.AddressDto;
import com.example.javaopencommerce.address.AddressEntity;


public interface AddressConverter {

    static Address convertToModel(AddressEntity address) {
        return Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .local(address.getLocal())
                .city(address.getCity())
                .zip(address.getZip())
                .build();
    }

    static Address convertDtoToModel(AddressDto address) {
        return Address.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .city(address.getCity())
                .local(address.getLocal())
                .street(address.getStreet())
                .zip(address.getZip())
                .build();
    }

    static AddressEntity convertToEntity(Address addressModel) {
        return AddressEntity.builder()
                .id(addressModel.getId())
                .userId(addressModel.getUserId())
                .street(addressModel.getStreet())
                .city(addressModel.getCity())
                .local(addressModel.getLocal())
                .zip(addressModel.getZip())
                .build();
    }

    static AddressDto convertToDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .street(address.getStreet())
                .local(address.getLocal())
                .city(address.getCity())
                .zip(address.getZip())
                .build();
    }
}
