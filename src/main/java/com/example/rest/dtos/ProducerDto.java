package com.example.rest.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDto {

    private Long id;
    private String name;
    private String description;
    private ImageDto image;
}
