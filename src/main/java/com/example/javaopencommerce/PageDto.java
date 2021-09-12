package com.example.javaopencommerce;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private List<T> items;

    private int pageNumber;
    private int pageSize;
    private int pageCount;
    private int totalElementsCount;

}
