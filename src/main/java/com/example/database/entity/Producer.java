package com.example.database.entity;

import lombok.*;

import java.util.List;

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
