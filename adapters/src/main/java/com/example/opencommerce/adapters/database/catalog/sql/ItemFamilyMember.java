package com.example.opencommerce.adapters.database.catalog.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class ItemFamilyMember {

    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;
    private String variantName;
}
