package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import java.util.List;
import lombok.Builder;

@Builder
record CardSnapshot(List<CardItemSnapshot> items, Value cardValueNett, Value cardValueGross) {

}
