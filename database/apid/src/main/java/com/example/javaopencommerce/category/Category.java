package com.example.javaopencommerce.category;

import java.util.ArrayList;
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
@EqualsAndHashCode
public class Category {

    private Long id;

    @Builder.Default
    private List<CategoryDetails> details = new ArrayList<>();
}
