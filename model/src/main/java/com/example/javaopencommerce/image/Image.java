package com.example.javaopencommerce.image;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class Image {

    Long id;
    String alt;
    String url;

    public ImageSnapshot getSnapshot() {
        return new ImageSnapshot(this.id, this.alt, this.url);
    }

    static Image restore(ImageSnapshot imageSnapshot) {
        return Image.builder()
                .id(imageSnapshot.getId())
                .alt(imageSnapshot.getAlt())
                .url(imageSnapshot.getUrl())
                .build();
    }

}
