package com.example.javaopencommerce.image;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImageSnapshot {

    Long id;
    String alt;
    String url;
    
}
