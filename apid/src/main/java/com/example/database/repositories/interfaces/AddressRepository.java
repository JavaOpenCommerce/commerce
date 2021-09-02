package com.example.database.repositories.interfaces;

import com.example.database.entity.Address;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface AddressRepository {

    Uni<List<Address>> findByZip(String zip);

    Uni<Address> findById(Long id);

    Uni<List<Address>> getAddressByCity(String city);

    Uni<List<Address>> getUserAddressListById(Long id);
}
