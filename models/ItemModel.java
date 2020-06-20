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
    private final String name;
    private final String description;
    private Value valueGross;
    private Vat vat;
    private ProducerModel producerModel;
    private Set<CategoryModel> category;
}
