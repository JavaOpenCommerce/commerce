package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Locale;

@Getter
@Builder
@EqualsAndHashCode
public class ItemDetailModel {

    private Long id;
    private String name;
    private String description;
    private Locale lang;
    private List<ImageModel> additionalImages;
}
