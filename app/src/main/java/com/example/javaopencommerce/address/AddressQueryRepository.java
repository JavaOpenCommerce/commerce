package com.example.javaopencommerce.address;

import com.example.javaopencommerce.address.dtos.AddressDto;
import io.smallrye.mutiny.Uni;
import java.util.List;


public interface AddressQueryRepository {

  Uni<List<AddressDto>> findByZip(String zip);

  Uni<AddressDto> findById(Long id);

  Uni<List<AddressDto>> getUserAddressListById(Long id);

}
