package com.example.javaopencommerce.order;

import com.example.javaopencommerce.address.AddressDto;
import com.example.javaopencommerce.user.UserDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {

    private Long id;
    private LocalDate creationDate;

    @Builder.Default
    private final String paymentStatus = "BEFORE_PAYMENT";

    @Builder.Default
    private final String paymentMethod = "MONEY_TRANSFER";

    @Builder.Default
    private final String orderStatus = "NEW";

    private AddressDto address;

    private CardDto card;

}
