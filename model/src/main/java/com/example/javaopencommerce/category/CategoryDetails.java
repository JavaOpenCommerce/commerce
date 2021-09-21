package com.example.javaopencommerce.category;

import java.util.Locale;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDetails {

    Long id;
    String name;
    String description;
    Locale lang;

}
