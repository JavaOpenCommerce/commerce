package com.example.javaopencommerce.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

    Long id;
    String firstName;
    String lastName;
    String email;

//    @Builder.Default
//    private List<UserType> permissions = new ArrayList<>();
}
