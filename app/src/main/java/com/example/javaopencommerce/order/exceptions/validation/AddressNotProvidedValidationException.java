package com.example.javaopencommerce.order.exceptions.validation;

public class AddressNotProvidedValidationException extends OrderValidationException {

  public AddressNotProvidedValidationException() {
    super("Order does not provide valid address id!");
  }
}
