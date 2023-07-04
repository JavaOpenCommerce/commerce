package com.example.javaopencommerce.exception;

import java.util.UUID;

import static java.lang.String.format;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType, UUID id) {
        super(format("%s with id: %s, not found!", entityType, id));
    }

    public EntityNotFoundException(String entityType, Number id) {
        super(format("%s with id: %s, not found!", entityType, id));
    }

    public EntityNotFoundException(String entityType) {
        super(format("%s not found!", entityType));
    }
}
