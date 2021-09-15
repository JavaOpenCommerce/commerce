package com.example.javaopencommerce.item;


import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.Image;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Item {

    private final Long id;
    private Value valueGross;
    private Vat vat;
    private Image image;
    private List<ItemDetails> details;
    private int stock;
}
