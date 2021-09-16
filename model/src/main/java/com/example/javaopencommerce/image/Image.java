package com.example.javaopencommerce.image;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Image {

    public static Image restore(ImageSnapshot imageSnapshot) {
        return Image.builder()
            .id(imageSnapshot.getId())
            .alt(imageSnapshot.getAlt())
            .url(imageSnapshot.getUrl())
            .build();
    }

    private Long id;
    private String alt;
    private String url;

    public ImageSnapshot getSnapshot() {
        return new ImageSnapshot(id, alt, url);
    }
}
