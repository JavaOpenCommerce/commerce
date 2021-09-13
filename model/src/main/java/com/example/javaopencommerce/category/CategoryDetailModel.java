package com.example.javaopencommerce.category;

import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CategoryDetailModel {

    private Long id;
    private String name;
    private String description;
    private Locale lang;
}
