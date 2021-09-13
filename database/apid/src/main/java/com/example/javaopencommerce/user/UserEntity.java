package com.example.javaopencommerce.user;


import com.example.javaopencommerce.address.AddressEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserEntity {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @Builder.Default
    private List<UserType> permissions = new ArrayList<>();

    @Builder.Default
    private List<AddressEntity> addresses = new ArrayList<>();

}
