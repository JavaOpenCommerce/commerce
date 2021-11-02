package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

interface ItemSearchService {

  Uni<JsonObject> searchItemsBySearchRequest(SearchRequest request);

}
