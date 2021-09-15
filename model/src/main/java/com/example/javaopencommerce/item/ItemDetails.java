package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.Image;
import java.util.List;
import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ItemDetails {

    private Long id;
    private String name;
    private String description;
    private Locale lang;
    private List<Image> additionalImages;
}
