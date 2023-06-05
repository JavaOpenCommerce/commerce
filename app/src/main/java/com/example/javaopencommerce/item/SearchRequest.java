package com.example.javaopencommerce.item;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {

  private int pageNum = 0;
  private int pageSize = 10;
  private String order = "ASC";
  private String sortBy = "name";

  private String searchQuery = "";

  private Double priceMin;
  private Double priceMax;

  @Setter(AccessLevel.NONE)
  private int[] categoryIds = new int[]{0};

  @Setter(AccessLevel.NONE)
  private int[] producerIds = new int[]{0};

  public void setCategoryIds(String ids) {
    categoryIds = getIdsArray(ids);
  }

  public void setProducerIds(String ids) {
    producerIds = getIdsArray(ids);
  }

  private int[] getIdsArray(String ids) {
    try {
      return stream(ids.split(","))
          .map(Integer::parseInt)
          .mapToInt(i -> i)
          .toArray();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(format("Illegal/corrupted ids query param: %s", ids));
    }
  }
}
