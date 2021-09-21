package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.Image;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Producer {

    Long id;
    Image image;
    List<ProducerDetails> details;

}
