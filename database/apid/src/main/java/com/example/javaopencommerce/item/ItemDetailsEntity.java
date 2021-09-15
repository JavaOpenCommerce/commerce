package com.example.javaopencommerce.item;


import com.example.javaopencommerce.image.ImageEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"images"})
public class ItemDetailsEntity {

    private Long id;
    private String name;
    private Locale lang;
    private String description;
    private Long itemId;

    @Builder.Default
    private List<ImageEntity> images = new ArrayList<>();

}
