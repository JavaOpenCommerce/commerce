package com.example.opencommerce.app.catalog.query;

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
