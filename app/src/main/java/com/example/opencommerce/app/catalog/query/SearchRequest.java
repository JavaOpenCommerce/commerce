package com.example.opencommerce.app.catalog.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.String.format;
import static java.util.Arrays.stream;

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
    private String[] categoryIds = new String[]{};

    @Setter(AccessLevel.NONE)
    private int[] producerIds = new int[]{};

    public void setCategoryIds(String ids) {
        categoryIds = getUUIDsArray(ids);
    }

    public void setProducerIds(String ids) {
        producerIds = getSerialIdsArray(ids);
    }

    private int[] getSerialIdsArray(String ids) {
        if (ids.isEmpty()) {
            return new int[]{};
        }

        try {
            return stream(ids.split(","))
                    .map(Integer::parseInt)
                    .mapToInt(i -> i)
                    .toArray();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(format("Illegal/corrupted ids query param: %s", ids));
        }
    }

    private String[] getUUIDsArray(String ids) {
        if (ids.isEmpty()) {
            return new String[]{};
        }

        try {
            return stream(ids.split(","))
                    .toArray(String[]::new);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(format("Illegal/corrupted ids query param: %s", ids));
        }
    }
}
