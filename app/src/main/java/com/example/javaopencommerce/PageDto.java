package com.example.javaopencommerce;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageDto<T> {

    List<T> items;

    int pageNumber;
    int pageSize;
    int pageCount;
    int totalElementsCount;

}
