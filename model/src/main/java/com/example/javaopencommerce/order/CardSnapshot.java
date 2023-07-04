package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import lombok.Builder;

import java.util.List;

@Builder
record CardSnapshot(List<CardItemSnapshot> items, Value cardValueNett, Value cardValueGross) {

}
