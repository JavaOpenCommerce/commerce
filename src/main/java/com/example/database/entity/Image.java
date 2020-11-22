package com.example.database.entity;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Image {

    private Long id;
    private String alt;
    private String url;
}
