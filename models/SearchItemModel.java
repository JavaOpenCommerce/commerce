package com.example.business.models;

import com.example.business.Value;
import com.example.rest.dtos.ImageDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SearchItemModel {

    private final Long id;
    private Value valueGross;
    private ImageDto image;
    private List<CategoryModel> categories;
    private List<ItemDetailModel> details;
    private List<ProducerModel> producer;
}
