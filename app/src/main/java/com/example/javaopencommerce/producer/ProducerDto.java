package com.example.javaopencommerce.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProducerDto {

    private Long id;
    private String name;
    private String description;
    private Long imageId;
}
