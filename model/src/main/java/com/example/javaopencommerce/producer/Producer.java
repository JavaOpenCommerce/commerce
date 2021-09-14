package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.Image;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Producer {

    private Long id;
    private Image image;
    private List<ProducerDetails> details;
}
