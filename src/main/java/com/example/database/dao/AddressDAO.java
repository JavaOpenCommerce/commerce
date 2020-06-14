package com.example.database.dao;

import com.example.database.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDAO {
    Optional<Address> getById(Long id);

    List<Address> getAll();

    List<Address> getAddressByZip(String zip);

    List<Address> getAddressByCity(String city);

}
