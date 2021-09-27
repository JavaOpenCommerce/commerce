package com.example.javaopencommerce.item.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

  private Long itemId;
  private int amount;
}
