package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ProducerModel {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
}
