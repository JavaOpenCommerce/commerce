package com.example.business.models;


import com.example.business.Value;
import com.example.business.Vat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ItemModel {

    private final Long id;
    private Value valueGross;
    private Vat vat;
    private ProducerModel producer;
    private ImageModel image;
    private Set<CategoryModel> category;
    private Set<ItemDetailModel> details;
    private int stock;
}
