package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.Image;
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
public class Producer {

    private Long id;
    private List<ProducerDetails> details;
    private Image image;
}
