package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.ImageEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "details")
public class ProducerEntity {

    private Long id;
    private List<ProducerDetailsEntity> details;
    private ImageEntity image;
}
