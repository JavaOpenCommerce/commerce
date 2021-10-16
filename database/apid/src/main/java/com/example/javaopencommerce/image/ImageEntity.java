package com.example.javaopencommerce.image;

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

class ImageEntity {

    private Long id;
    private String alt;
    private String url;

    public Image toImageModel() {
        return Image.builder()
                .id(this.id)
                .alt(this.alt)
                .url(this.url)
                .build();
    }

    public static ImageEntity fromSnapshot(ImageSnapshot imageSnapshot) {
        return ImageEntity.builder()
                .id(imageSnapshot.getId())
                .alt(imageSnapshot.getAlt())
                .url(imageSnapshot.getUrl())
                .build();
    }
}
