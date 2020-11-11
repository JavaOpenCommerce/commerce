package com.example.business.models;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

//    @Builder.Default
//    private List<UserType> permissions = new ArrayList<>();

    @Builder.Default
    private List<AddressModel> addresses = new ArrayList<>();
}
