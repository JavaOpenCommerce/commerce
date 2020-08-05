package com.example.elasticsearch;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class SearchDetails {

    private String lang;
    private String name;
    private String description;
}
