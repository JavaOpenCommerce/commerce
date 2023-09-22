package com.example.opencommerce.domain.pricing.events;


import com.example.opencommerce.domain.Value;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class BasePriceChangedEvent extends PriceEvent {

    private final BigDecimal newPrice;
    private final Instant validFrom;

    public BasePriceChangedEvent(Instant validFrom, Value newPrice) {
        this.newPrice = newPrice.asDecimal();
        this.validFrom = validFrom;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public Instant getValidFrom() {
        return validFrom;
    }
}
