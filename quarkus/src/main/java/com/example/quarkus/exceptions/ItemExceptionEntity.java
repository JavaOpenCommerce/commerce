package com.example.quarkus.exceptions;

import lombok.Getter;

@Getter
public final class ItemExceptionEntity {

    private final Long itemId;
    private final String message;

    private ItemExceptionEntity(Long itemId, String message) {
        this.itemId = itemId;
        this.message = message;
    }

    public static ItemExceptionEntity create(Long itemId, String message) {
        return new ItemExceptionEntity(itemId, message);
    }
}
