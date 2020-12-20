package com.example.database.entity;

import lombok.*;

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
