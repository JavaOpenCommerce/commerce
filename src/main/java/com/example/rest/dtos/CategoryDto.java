package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CategoryDto {

    private Long id;
    private String categoryName;
    private String description;
}
