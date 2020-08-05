package com.example.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private List<T> items;

    private int pageNumber;
    private int pageSize;
    private int pageCount;
    private int totalElementsCount;

}
