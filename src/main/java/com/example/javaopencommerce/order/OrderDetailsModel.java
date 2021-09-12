package com.example.javaopencommerce.order;

import com.example.javaopencommerce.address.AddressModel;
import com.example.javaopencommerce.user.UserModel;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class OrderDetailsModel {

    private final Long id;
    private final LocalDate creationDate;

    @Builder.Default
    private final String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private final String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private final String orderStatus = "NEW";

    private final AddressModel address;

    private final CardModel card;
}
