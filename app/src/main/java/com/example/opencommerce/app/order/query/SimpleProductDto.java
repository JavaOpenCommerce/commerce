package com.example.opencommerce.app.order.query;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
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
