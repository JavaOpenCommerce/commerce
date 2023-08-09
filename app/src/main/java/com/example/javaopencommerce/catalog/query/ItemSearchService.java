package com.example.javaopencommerce.catalog.query;

import io.vertx.core.json.JsonObject;

public interface ItemSearchService {

    JsonObject searchProductsBySearchRequest(SearchRequest request);

}
