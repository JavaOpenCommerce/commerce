package com.example.javaopencommerce;

import lombok.Value;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Value
public class Vat {

    double value;

    private Vat(double vat) {
        this.value = vat;
    }

    public static Vat of(double vat) {
        if (vat < 0.00) {
            vat = 0.00;
        }
        return new Vat(vat);
    }

    public BigDecimal asDecimal() {
        return valueOf(this.value);
    }

    public double asDouble() {
        return this.value;
    }

}
