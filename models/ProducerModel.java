package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Locale;

@Getter
@Builder
@EqualsAndHashCode
public class ProducerModel {

    private Long id;
    private String name;
    private String description;
    private ImageModel image;
    private Locale lang;
}
