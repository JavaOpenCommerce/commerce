package com.example.javaopencommerce.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardProductEntity {

    private Long itemId;
    private int amount;

    static CardProductEntity fromSnapshot(ProductSnapshot productSnapshot) {
        return CardProductEntity.builder()
            .itemId(productSnapshot.getItemId())
            .amount(productSnapshot.getAmount().asInteger())
            .build();
    }
}
