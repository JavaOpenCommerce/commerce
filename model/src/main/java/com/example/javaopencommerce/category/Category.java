package com.example.javaopencommerce.category;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Category {

    private Long id;
    private List<CategoryDetails> details;
}
