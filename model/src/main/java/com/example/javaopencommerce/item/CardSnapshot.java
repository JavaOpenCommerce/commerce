package com.example.javaopencommerce.item;

import com.example.javaopencommerce.Value;
import java.util.List;
import lombok.Builder;

@Builder
record CardSnapshot(List<ProductSnapshot> products, Value cardValueNett, Value cardValueGross) {

}
