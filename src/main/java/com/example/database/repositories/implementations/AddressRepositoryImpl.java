package com.example.database.repositories.implementations;

import com.example.database.entity.Address;
import com.example.database.repositories.interfaces.AddressRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

//TODO
@ApplicationScoped
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public Address findByZip(String zip) {
        return null;
    }

    @Override
    public List<Address> getAddressByCity(String city) {
        return null;
    }

    @Override
    public List<Address> getUserAddressListById(Long id) {
        return null;
    }
}
