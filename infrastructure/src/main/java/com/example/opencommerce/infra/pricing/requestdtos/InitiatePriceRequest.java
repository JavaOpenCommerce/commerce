package com.example.opencommerce.infra.pricing.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiatePriceRequest {

    private BigDecimal basePrice;
    private BigDecimal vat;
}
