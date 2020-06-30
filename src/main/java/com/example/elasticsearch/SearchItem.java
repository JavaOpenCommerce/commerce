package com.example.elasticsearch;

import com.example.rest.dtos.ImageDto;
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
    private Long producerId;
    private Set<Long> categoryIds;
    private Set<SearchDetails> details;
}
