package com.example.database.entity;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CardProduct {

    private Long itemId;
    private int amount;
}
