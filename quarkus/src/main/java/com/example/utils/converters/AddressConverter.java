package com.example.utils.converters;

import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.address.AddressDto;
import com.example.javaopencommerce.address.AddressModel;


public interface AddressConverter {

    static AddressModel convertToModel(AddressEntity address) {
        return AddressModel.builder()
                .id(address.getId())
                .street(address.getStreet())
                .local(address.getLocal())
                .city(address.getCity())
                .zip(address.getZip())
                .build();
    }

    static AddressModel convertDtoToModel(AddressDto address) {
        return AddressModel.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .city(address.getCity())
                .local(address.getLocal())
                .street(address.getStreet())
                .zip(address.getZip())
                .build();
    }

    static AddressEntity convertToEntity(AddressModel addressModel) {
        return AddressEntity.builder()
                .id(addressModel.getId())
                .userId(addressModel.getUserId())
                .street(addressModel.getStreet())
                .city(addressModel.getCity())
                .local(addressModel.getLocal())
                .zip(addressModel.getZip())
                .build();
    }

    static AddressDto convertToDto(AddressModel address) {
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
