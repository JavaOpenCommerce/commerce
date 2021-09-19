package com.example.javaopencommerce.category;

import lombok.Builder;
import lombok.Value;

import java.util.Locale;

@Value
@Builder
public class CategoryDetails {

    Long id;
    String name;
    String description;
    Locale lang;

}
