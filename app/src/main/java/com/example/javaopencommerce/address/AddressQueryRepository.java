package com.example.javaopencommerce.address;

import com.example.javaopencommerce.address.dtos.AddressDto;
import java.util.List;


public interface AddressQueryRepository {

  List<AddressDto> findByZip(String zip);

  AddressDto findById(Long id);

  List<AddressDto> getUserAddressListById(Long id);

}
