package com.example.javaopencommerce.category;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Category {

    Long id;
    List<CategoryDetails> details;

}
