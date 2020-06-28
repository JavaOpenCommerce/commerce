package com.example.elasticsearch;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Locale;

@Getter
@Builder
@EqualsAndHashCode
public class SearchDetails {

    private Locale lang;
    private String name;
    private String description;
}
