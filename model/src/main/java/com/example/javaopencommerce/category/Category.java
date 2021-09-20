package com.example.javaopencommerce.category;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Category {

    Long id;
    List<CategoryDetails> details;

}
