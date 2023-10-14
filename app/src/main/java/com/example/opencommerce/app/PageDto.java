package com.example.opencommerce.app;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageDto<T> {

    public static <T> PageDto<T> fromCommand(CreatePageCommand<T> command) {
        int totalCount = command.total();
        int pageSize = command.pageSize();
        int pageCount = Double.valueOf(Math.ceil(totalCount / (double) pageSize))
                .intValue();
        return new PageDto<>(command.items(), command.pageNumber(), pageSize, pageCount, totalCount);
    }

    List<T> items;

    int pageNumber;
    int pageSize;
    int pageCount;
    int totalElementsCount;

    public record CreatePageCommand<T>(List<T> items, int total, int pageNumber, int pageSize) {}

}
