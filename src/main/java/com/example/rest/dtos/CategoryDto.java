package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Locale;

@Getter
@Builder
@EqualsAndHashCode
public class CategoryDto {

    private Long id;
    private String categoryName;
    private String description;
    private Locale lang;
}
