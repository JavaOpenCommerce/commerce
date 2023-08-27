package com.example.opencommerce.domain.pricing.events;

import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class NewPriceInitiatedEvent extends PriceEvent {

    private final BigDecimal vat;
    private final BigDecimal basePrice;

    public NewPriceInitiatedEvent(Value basePrice, Vat vat) {
        this.vat = vat.asDecimal();
        this.basePrice = basePrice.asDecimal();
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    @Override
    public Instant getValidFrom() {
        return Instant.now();
    }
}
