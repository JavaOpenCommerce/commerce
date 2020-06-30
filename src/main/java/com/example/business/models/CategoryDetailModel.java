package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Locale;

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
