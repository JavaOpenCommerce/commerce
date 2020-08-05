package com.example.rest.dtos;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ImageDto {

    private Long id;
    private String alt;
    private String url;
}
