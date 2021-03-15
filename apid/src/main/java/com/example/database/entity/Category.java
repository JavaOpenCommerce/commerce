package com.example.database.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Category {

    private Long id;

    @Builder.Default
    private List<CategoryDetails> details = new ArrayList<>();
}
