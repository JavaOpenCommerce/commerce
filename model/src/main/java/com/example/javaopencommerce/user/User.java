package com.example.javaopencommerce.user;

import com.example.javaopencommerce.address.Address;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class User {

    Long id;
    String firstName;
    String lastName;
    String email;

//    @Builder.Default
//    private List<UserType> permissions = new ArrayList<>();

    @Builder.Default
    List<Address> addresses = new ArrayList<>();
}
