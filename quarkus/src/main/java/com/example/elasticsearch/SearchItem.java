package com.example.elasticsearch;


import com.example.javaopencommerce.image.ImageDto;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
