package com.example.javaopencommerce.order.exceptions;

public class AddressNotProvidedValidationException extends OrderValidationException {

  public AddressNotProvidedValidationException() {
    super("Order does not provide valid address id!");
  }
}
