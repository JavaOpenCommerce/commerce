package com.example.javaopencommerce.user;

import com.example.javaopencommerce.address.Address;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

//    @Builder.Default
//    private List<UserType> permissions = new ArrayList<>();

    @Builder.Default
    private List<Address> addresses = new ArrayList<>();
}
