package com.example.javaopencommerce.image;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageSnapshot {

  private final Long id;
  private final String alt;
  private final String url;

  public ImageSnapshot(Long id, String alt, String url) {
    this.id = id;
    this.alt = alt;
    this.url = url;
  }
}
