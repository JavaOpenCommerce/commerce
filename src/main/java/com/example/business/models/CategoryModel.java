package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CategoryModel {

    private Long id;
    private Set<CategoryDetailModel> details;
}
