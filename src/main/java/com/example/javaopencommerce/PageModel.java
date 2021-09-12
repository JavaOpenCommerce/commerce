package com.example.javaopencommerce;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageModel<T> {

    private List<T> items;

    private int pageNumber;
    private int pageSize;
    private int pageCount;
    private int totalElementsCount;

}
