package com.example.javaopencommerce.order.exceptions;

public class UnknownPaymentMethodException extends
    RuntimeException {

  private static final String ERROR_MSG_TEMPLATE = "Unknown payment method: %s, allowed methods: [%s].";

  public UnknownPaymentMethodException(String paymentMethod, String allowed) {
    super(String.format(ERROR_MSG_TEMPLATE, paymentMethod, allowed));
  }
}
