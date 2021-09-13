package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageDto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private int stock;
    private BigDecimal valueGross;
    private ImageDto image;
    private double vat;

}
