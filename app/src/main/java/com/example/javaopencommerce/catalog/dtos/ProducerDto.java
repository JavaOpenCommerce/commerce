package com.example.javaopencommerce.catalog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProducerDto {

    private Long id;
    private String name;
    private String logoUrl;
}
