package com.example.javaopencommerce.statics;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MessagesStore {

    public static final String OK = "OK";
    public static final String OUT_OF_STOCK = "item out of stock";
    public static final String BELOW_STOCK = "no more items in stock";
    public static final String ITEM_404 = "item already deleted";
}
