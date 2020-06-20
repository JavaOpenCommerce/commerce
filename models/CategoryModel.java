package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CategoryModel {

    private Long id;
    private String categoryName;
    private String description;
}
