package com.example.opencommerce.infra.catalog;

import com.example.opencommerce.app.catalog.query.SearchRequest;
import com.example.opencommerce.infra.catalog.views.BaseItemView;

import java.util.Comparator;
import java.util.List;

class ItemSorter {

    List<BaseItemView> sortItems(List<BaseItemView> items, SearchRequest request) {
        String sortingType = request.getSortBy()
                .toUpperCase() + "-" + request.getOrder()
                .toUpperCase();
        return items.stream()
                .sorted(pickItemComparator(sortingType))
                .toList();
    }

    private Comparator<BaseItemView> pickItemComparator(String sortingType) {
        return switch (sortingType) {
            case "VALUE-ASC" -> Comparator.comparing(BaseItemView::getValueGross);
            case "VALUE-DESC" -> Comparator.comparing(BaseItemView::getValueGross)
                    .reversed();
            case "NAME-DESC" -> Comparator.comparing(BaseItemView::getName)
                    .reversed();
            default -> Comparator.comparing(BaseItemView::getName);
        };
    }
}
