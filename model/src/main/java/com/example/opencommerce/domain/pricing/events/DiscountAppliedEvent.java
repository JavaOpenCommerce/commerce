package com.example.opencommerce.domain.pricing.events;


import com.example.opencommerce.domain.Value;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class DiscountAppliedEvent extends PriceEvent {
    private final BigDecimal discount;
    private final Instant validFrom;

    public DiscountAppliedEvent(Instant validFrom, Value discount) {
        this.discount = discount.asDecimal();
        this.validFrom = validFrom;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Instant getValidFrom() {
        return validFrom;
    }
}
