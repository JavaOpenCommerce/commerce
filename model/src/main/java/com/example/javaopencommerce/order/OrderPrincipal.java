package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.exceptions.UnknownPaymentMethodException;

import java.util.UUID;

import static java.lang.String.join;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

class OrderPrincipal {

    private final UUID id;
    private final UUID addressId;
    private final PaymentMethod paymentMethod;

    OrderPrincipal(UUID id, UUID addressId, String paymentMethod) {
        requireNonNull(id);
        requireNonNull(addressId);
        requireNonNull(paymentMethod);
        this.id = id;
        this.addressId = addressId;
        this.paymentMethod = parsePaymentMethod(paymentMethod);
    }

    private static PaymentMethod parsePaymentMethod(String paymentMethod) {
        try {
            return PaymentMethod.valueOf(paymentMethod);
        } catch (IllegalArgumentException e) {
            throw new UnknownPaymentMethodException(paymentMethod,
                    join(", ", stream(PaymentMethod.values()).map(PaymentMethod::name)
                            .toList()));
        }
    }

    OrderPrincipalSnapshot getSnapshot() {
        return new OrderPrincipalSnapshot(id, addressId, paymentMethod);
    }

    record OrderPrincipalSnapshot(UUID id, UUID addressId, PaymentMethod paymentMethod) {

    }
}
