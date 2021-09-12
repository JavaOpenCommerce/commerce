package com.example.javaopencommerce.item;

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
public class ItemQuantity {

    private Long id;
    private int amount;
    private Long itemId;
    private Long orderId;
}
