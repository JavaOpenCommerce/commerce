package com.example.javaopencommerce.address;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface AddressRepository {

    Uni<List<AddressEntity>> findByZip(String zip);

    Uni<AddressEntity> findById(Long id);

    Uni<List<AddressEntity>> getAddressByCity(String city);

    Uni<List<AddressEntity>> getUserAddressListById(Long id);
}
