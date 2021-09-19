package com.example.javaopencommerce.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    public static ImageDto fromSnapshot(ImageSnapshot imageSnapshot) {
        return ImageDto.builder()
            .id(imageSnapshot.getId())
            .alt(imageSnapshot.getAlt())
            .url(imageSnapshot.getUrl())
            .build();
    }

    private Long id;
    private String alt;
    private String url;
}
