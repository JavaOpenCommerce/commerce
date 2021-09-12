package com.example.javaopencommerce.item;


import com.example.javaopencommerce.image.ImageModel;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ItemModel {

    private final Long id;
    private Value valueGross;
    private Vat vat;
    private ImageModel image;
    private List<ItemDetailModel> details;
    private int stock;
}
