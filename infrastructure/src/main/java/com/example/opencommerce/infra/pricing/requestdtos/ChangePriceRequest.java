package com.example.opencommerce.infra.pricing.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePriceRequest {

    private BigDecimal newPrice;
    private Instant validFrom = Instant.now();
}
