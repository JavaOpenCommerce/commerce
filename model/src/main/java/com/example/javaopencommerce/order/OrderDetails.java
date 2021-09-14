package com.example.javaopencommerce.order;

import com.example.javaopencommerce.address.Address;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class OrderDetails {

    private final Long id;
    private final LocalDate creationDate;

    @Builder.Default
    private final String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private final String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private final String orderStatus = "NEW";

    private final Address address;

    private final Card card;
}
