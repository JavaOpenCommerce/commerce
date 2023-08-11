package com.example.opencommerce.app.catalog.query;

import io.vertx.core.json.JsonObject;

public interface ItemSearchService {

    JsonObject searchProductsBySearchRequest(SearchRequest request);

}
