package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class UserModel {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;

    @Builder.Default
    private final List<AddressModel> addresses = new ArrayList<>();

    //TODO LATER
    //private List<UserType> permissions = new ArrayList<>();
}
