package com.example.javaopencommerce.elasticsearch;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class SearchDetails {

    private final String lang;
    private final String name;
    private final String description;
}
