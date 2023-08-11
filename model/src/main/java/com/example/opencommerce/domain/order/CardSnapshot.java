package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.Value;
import lombok.Builder;

import java.util.List;

@Builder
public record CardSnapshot(List<CardItem.CardItemSnapshot> items, Value cardValueNett, Value cardValueGross) {

}
