package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.Image;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Producer {

    Long id;
    Image image;
    List<ProducerDetails> details;

}
