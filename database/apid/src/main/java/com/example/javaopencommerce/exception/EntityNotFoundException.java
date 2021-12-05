package com.example.javaopencommerce.exception;

import static java.lang.String.format;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String entityType, Long id) {
    super(format("%s with id: %s, not found!", entityType, id));
  }
}
