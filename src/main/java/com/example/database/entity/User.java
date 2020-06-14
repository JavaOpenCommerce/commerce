package com.example.database.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    private String email;

    @Builder.Default
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<UserType> permissions = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH}, mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();

}
