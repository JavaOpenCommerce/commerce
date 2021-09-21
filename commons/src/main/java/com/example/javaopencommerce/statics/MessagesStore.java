package com.example.javaopencommerce.statics;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class MessagesStore {

    public static final String OK = "OK";
    public static final String OUT_OF_STOCK = "item out of stock";
    public static final String BELOW_STOCK = "no more items in stock";
    public static final String ITEM_404 = "item already deleted";
}
