package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.producer.ProducerDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDto {

    private Long id;
    private String name;
    private String description;
    private ImageDto mainImage;
    private ProducerDto producer;
    private List<ImageDto> additionalImages;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
}
