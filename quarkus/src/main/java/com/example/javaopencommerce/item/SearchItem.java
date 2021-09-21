package com.example.javaopencommerce.item;

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
    private final double valueGross;
    private final ImageDto image;
    private final Long producerId;
    private final List<SearchItemDetails> details;
}
