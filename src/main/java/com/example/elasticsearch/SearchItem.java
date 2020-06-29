package com.example.elasticsearch;

import com.example.rest.dtos.CategoryDto;
import com.example.rest.dtos.ImageDto;
import com.example.rest.dtos.ProducerDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@EqualsAndHashCode
public class SearchItem {

    private final Long id;
    private double valueGross;
    private ImageDto image;

    private Set<ProducerDto> producer;
    private Set<CategoryDto> category;
    private Set<SearchDetails> details;
}
