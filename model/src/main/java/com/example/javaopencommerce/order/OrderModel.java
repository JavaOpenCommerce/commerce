package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@lombok.Value
@Builder
@Getter(AccessLevel.NONE)
class OrderModel {

    Long id;
    LocalDate creationDate;

    @Builder.Default
    PaymentStatus paymentStatus = PaymentStatus.BEFORE_PAYMENT;

    @Builder.Default
    PaymentMethod paymentMethod = PaymentMethod.MONEY_TRANSFER;

    @Builder.Default
    OrderStatus orderStatus = OrderStatus.NEW;

    Value orderValueGross;
    Value orderValueNett;
    List<SimpleProduct> orderBody;

    @lombok.Value
    @Builder
    static class SimpleProduct {
        Long itemId;
        String name;
        Amount amount;
        Value valueGross;
        Vat vat;
    }

    OrderSnapshot getSnapshot() {
        return OrderSnapshot.builder()
            .id(id)
            .orderValueNett(orderValueNett)
            .orderValueGross(orderValueGross)
            .creationDate(creationDate)
            .orderStatus(orderStatus)
            .paymentMethod(paymentMethod)
            .paymentStatus(paymentStatus)
            .orderBody(Collections.unmodifiableList(orderBody))
            .build();
    }
}

