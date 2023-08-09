package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;

import java.math.BigDecimal;

class TestItems {

    static final Item TEST_ITEM_1 = new Item(ItemId.of(1L), Amount.of(5), "Item1",
            "http://images.com/1", Value.of(
            BigDecimal.valueOf(10.01)), Vat.of(0.23));
    static final Item TEST_ITEM_2 = new Item(ItemId.of(2L), Amount.of(3), "Item1",
            "http://images.com/1", Value.of(
            BigDecimal.valueOf(9.99)), Vat.of(0.23));
    static final Item TEST_ITEM_3 = new Item(ItemId.of(3L), Amount.of(1), "Item1",
            "http://images.com/1", Value.of(
            BigDecimal.valueOf(2)), Vat.of(0.23));

}