package com.example.opencommerce.infra.catalog.views;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PriceView {

    BigDecimal basePriceNett;
    BigDecimal basePriceGross;
    BigDecimal vat;
    DiscountView discount;

    @Value
    public static class DiscountView {
        BigDecimal discountedPriceNett;
        BigDecimal discountedPriceGross;
        BigDecimal lowestPriceBeforeDiscountGross;
    }
}
