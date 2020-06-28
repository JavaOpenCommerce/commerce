package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ImageDto {

    private Long id;
    private String alt;
    private String url;
}
