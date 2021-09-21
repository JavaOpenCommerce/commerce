package com.example.javaopencommerce.category;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String categoryName;
    private String description;
    private Locale lang;
}
