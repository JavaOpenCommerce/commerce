package com.example.javaopencommerce.item;

import io.vertx.core.json.JsonObject;

interface ItemSearchService {

  JsonObject searchItemsBySearchRequest(SearchRequest request);

}
