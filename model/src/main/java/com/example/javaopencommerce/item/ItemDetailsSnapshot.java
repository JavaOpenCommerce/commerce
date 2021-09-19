package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Getter;

@Getter
class ItemDetailsSnapshot {

  private final Long id;
  private final String name;
  private final String description;
  private final Locale lang;
  private final List<ImageSnapshot> additionalImages = new ArrayList<>();

  public ItemDetailsSnapshot(Long id, String name, String description, Locale lang,
      List<ImageSnapshot> additionalImages) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.lang = lang;
    this.additionalImages.addAll(additionalImages);
  }
}
