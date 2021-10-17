package com.example.javaopencommerce.producer;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Producer {

    Long id;
    Long imageId;
    List<ProducerDetails> details;

}
