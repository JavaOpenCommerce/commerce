package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.ImageModel;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ProducerModel {

    private Long id;
    private ImageModel image;
    private List<ProducerDetailModel> details;
}
