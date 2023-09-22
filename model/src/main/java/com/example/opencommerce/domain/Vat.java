package com.example.opencommerce.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

@lombok.Value
public class Vat {

    public final static Vat ZERO = new Vat(0);

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

    public static Vat of(BigDecimal vat) {
        if (vat.signum() < 0) {
            vat = BigDecimal.ZERO;
        }
        return new Vat(vat.doubleValue());
    }

    public Value toNett(Value gross) {
        return gross.divide(asDecimal().add(ONE));
    }

    public BigDecimal asDecimal() {
        return valueOf(this.value);
    }

    public double asDouble() {
        return this.value;
    }

}
