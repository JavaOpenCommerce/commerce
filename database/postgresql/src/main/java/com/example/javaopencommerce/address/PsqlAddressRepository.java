package com.example.javaopencommerce.address;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface PsqlAddressRepository {

  Uni<List<AddressEntity>> findByZip(String zip);

  Uni<AddressEntity> findById(Long id);

  Uni<List<AddressEntity>> getUserAddressListById(Long id);

}
