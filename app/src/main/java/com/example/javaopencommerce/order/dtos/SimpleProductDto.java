package com.example.javaopencommerce.order.dtos;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleProductDto {

    private Long itemId;
    private String name;
    private Amount amount;
    private Value valueGross;
    private Vat vat;
}
