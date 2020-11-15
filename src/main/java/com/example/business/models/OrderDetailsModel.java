package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class OrderDetailsModel {

    private final Long id;
    private final LocalDate creationDate;
    private AddressModel address;

    @Builder.Default
    private String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private String orderStatus = "NEW";

    private List<ProductModel> products;

    private UserModel user;
}
