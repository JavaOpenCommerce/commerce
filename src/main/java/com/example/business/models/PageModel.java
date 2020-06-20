package com.example.business.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
