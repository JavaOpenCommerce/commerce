package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
public class OrderDetailsDto {

    private final Long id;
    private final LocalDate creationDate;

    @Builder.Default
    private final String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private final String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private final String orderStatus = "NEW";

    private AddressDto address;

    private UserDto user;

    private final CardDto card;

}
