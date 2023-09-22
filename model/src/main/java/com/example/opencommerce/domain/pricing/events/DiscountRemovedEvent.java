package com.example.opencommerce.domain.pricing.events;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class DiscountRemovedEvent extends PriceEvent {
    private final Instant validFrom;

    public DiscountRemovedEvent(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidFrom() {
        return validFrom;
    }
}
