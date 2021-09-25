package com.example.javaopencommerce.order;

import com.example.javaopencommerce.address.Address;
import com.example.javaopencommerce.item.Card;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDetails {

    Long id;
    LocalDate creationDate;

    @Builder.Default
    String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    String orderStatus = "NEW";

    Address address;

    Card card;
}

