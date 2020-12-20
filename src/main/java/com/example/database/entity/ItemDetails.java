package com.example.database.entity;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"images"})
public class ItemDetails {

    private Long id;
    private String name;
    private Locale lang;
    private String description;
    private Long itemId;

    @Builder.Default
    private List<Image> images = new ArrayList<>();

}
