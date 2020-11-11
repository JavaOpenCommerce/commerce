package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
@EqualsAndHashCode
public class OrderDetailsModel {

    private final Long id;
    private final LocalDate creationDate;
    private final AddressModel address;

    @Builder.Default
    private String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private String orderStatus = "NEW";

    private final UserModel user;
    //????setter?
    private final Map<Long, ProductModel> products;
}
