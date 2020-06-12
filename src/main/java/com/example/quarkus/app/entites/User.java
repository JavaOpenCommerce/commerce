package com.example.quarkus.app.entites;


import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private Set<Address> addresses = new HashSet<>();
    private UserType userType;

}
