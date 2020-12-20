package com.example.database.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {

    private Long id;
    private String street;
    private String local;
    private String city;
    private String zip;
    private Long userId;
}
