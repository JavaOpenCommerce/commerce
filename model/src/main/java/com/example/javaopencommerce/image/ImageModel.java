package com.example.javaopencommerce.image;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ImageModel {

    private Long id;
    private String alt;
    private String url;
}
