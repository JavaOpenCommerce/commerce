package com.example.opencommerce.adapters.database.catalog.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_family")
class ItemFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "item_family_item",
            joinColumns = @JoinColumn(name = "item_family_id")
    )
    private List<ItemFamilyMember> family;
}
