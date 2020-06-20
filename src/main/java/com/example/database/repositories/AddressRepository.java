package com.example.database.repositories;

import com.example.database.entity.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {

    public Address findByZip(String zip) {
        return find("zip LIKE ?1", "%" + zip.trim() + "%").firstResult();
    }

    public List<Address> getAddressByCity(String city) {
        return list("city", city);
    }

    public List<Address> getUserAddressListById(Long id) {
        return list("user.id", id);
    }

}
