package com.example.business.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@EqualsAndHashCode
public class ProducerModel {

    private Long id;
    private ImageModel image;
    private Set<ProducerDetailModel> details;
}
