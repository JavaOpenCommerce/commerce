package com.example.database.repositories.interfaces;

import com.example.database.entity.Address;

import java.util.List;

public interface AddressRepository {

    Address findByZip(String zip);

    List<Address> getAddressByCity(String city);

    List<Address> getUserAddressListById(Long id);
}
