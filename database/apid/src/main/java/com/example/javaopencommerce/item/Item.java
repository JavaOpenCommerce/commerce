package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.Image;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Item {

    private Long id;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
    private Image image;

    @Builder.Default
    private List<ItemDetails> details = new ArrayList<>();

}
