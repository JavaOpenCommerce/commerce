package com.example.elasticsearch;

import com.example.rest.dtos.ImageDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class SearchItem {

    private final Long id;
    private double valueGross;
    private ImageDto image;
    private Long producerId;
    private List<Long> categoryIds;
    private List<SearchDetails> details;
}
