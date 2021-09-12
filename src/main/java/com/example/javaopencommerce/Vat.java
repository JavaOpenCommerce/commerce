package com.example.javaopencommerce;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

public final class Vat {

    private final double vat;

    private Vat(double vat) {this.vat = vat;}

    public static Vat of(double vat) {
        if (vat < 0.00) {
            vat = 0.00;
        }
        return new Vat(vat);
    }

    public BigDecimal asDecimal() {
        return valueOf(vat);
    }

    public double asDouble() {
        return vat;
    }
}
