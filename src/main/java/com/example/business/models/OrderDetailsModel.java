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

    private Long id;
    private LocalDate creationDate;
    private AddressModel address;

    @Builder.Default
    private String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private String orderStatus = "NEW";

    private Map<Long, ProductModel> products;

    private UserModel user;
}
