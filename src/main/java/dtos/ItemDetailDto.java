package dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
