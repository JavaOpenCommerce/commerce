package com.example.javaopencommerce.catalog;

import io.vertx.core.json.JsonObject;

interface ItemSearchService {

  JsonObject searchProductsBySearchRequest(SearchRequest request);

}
