package com.example.javaopencommerce;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PageDto<T> {

  List<T> items;

  int pageNumber;
  int pageSize;
  int pageCount;
  int totalElementsCount;

}
