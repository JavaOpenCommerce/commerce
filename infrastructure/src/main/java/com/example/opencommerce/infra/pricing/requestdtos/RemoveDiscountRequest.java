package com.example.opencommerce.infra.pricing.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveDiscountRequest {

    private Instant validFrom = Instant.now();
}
