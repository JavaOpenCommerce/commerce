package com.example.javaopencommerce.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
