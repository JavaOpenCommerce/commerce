package com.example.utils.converters;

import com.example.business.models.AddressModel;
import com.example.database.entity.Address;


public interface AddressConverter {

    static AddressModel convertToModel(Address address) {
        return AddressModel.builder()
                .id(address.getId())
                .street(address.getStreet())
                .local(address.getLocal())
                .city(address.getCity())
                .zip(address.getZip())
                .build();
    }
}
