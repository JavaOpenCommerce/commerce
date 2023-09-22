package com.example.opencommerce.domain.pricing;

import com.example.opencommerce.domain.Value;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

class HistoricalPrice {

    private final Instant validTo;
    private final Value priceNett;

    private HistoricalPrice(Instant validTo, Value priceNett) {
        this.validTo = requireNonNull(validTo);
        this.priceNett = requireNonNull(priceNett);
    }

    static HistoricalPrice at(Instant validFrom, Value priceNett) {
        return new HistoricalPrice(validFrom, priceNett);
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Value getPriceNett() {
        return priceNett;
    }
}
