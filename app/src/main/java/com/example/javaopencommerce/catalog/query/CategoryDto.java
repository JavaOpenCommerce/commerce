package com.example.javaopencommerce.catalog.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private UUID id;
    private String name;
    private String description;
    private List<CategoryDto> children;
}
