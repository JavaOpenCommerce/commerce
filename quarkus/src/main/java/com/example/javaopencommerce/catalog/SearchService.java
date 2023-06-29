package com.example.javaopencommerce.catalog;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import com.example.javaopencommerce.elasticsearch.ElasticAddress;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@Slf4j
@ApplicationScoped
class SearchService implements ItemSearchService {

  private final WebClient client;

  SearchService(Vertx vertx, ElasticAddress address) {
    this.client = WebClient.create(vertx, new WebClientOptions()
        .setDefaultPort(address.getPort())
        .setDefaultHost(address.getHost()));
  }

  public JsonObject searchProductsBySearchRequest(SearchRequest request) {
    SearchSourceBuilder ssb = new SearchSourceBuilder();
    String query = ssb.query(QueryBuilders.boolQuery()
            .must(ifTrueOrElse(request.getPriceMin() == null,
                QueryBuilders::matchAllQuery,
                () -> rangeQuery("valueGross").gte(request.getPriceMin())))
            .must(ifTrueOrElse(request.getPriceMax() == null,
                QueryBuilders::matchAllQuery,
                () -> rangeQuery("valueGross").lte(request.getPriceMax())))
            .must(ifTrueOrElse(noCategoryCriteria(request),
                QueryBuilders::matchAllQuery,
                () -> termsQuery("categoryIds.keyword", request.getCategoryIds())))
            .must(ifTrueOrElse(noProducerCriteria(request),
                QueryBuilders::matchAllQuery,
                () -> termsQuery("producerId", request.getProducerIds())))
            .must(ifTrueOrElse(request.getSearchQuery() == null || request.getSearchQuery().isEmpty(),
                QueryBuilders::matchAllQuery,
                () -> multiMatchQuery(request.getSearchQuery(), "name",
                    "description"))))
        .from(request.getPageSize() * request.getPageNum())
        .size(request.getPageSize())
        .toString();
    log.info(query);

    HttpResponse<Buffer> resp = client.get(
            "/items/_search?filter_path=hits.hits._id,hits.total.value")
        .putHeader("Content-Length", String.valueOf(query.length()))
        .putHeader("Content-Type", "application/json")
        .sendJsonObject(new JsonObject(query)).await().indefinitely();

    if (resp.statusCode() == 200) {
      return resp.bodyAsJsonObject();
    } else {
      return new JsonObject()
          .put("code", resp.statusCode())
          .put("message", resp.bodyAsString());
    }
  }

  private QueryBuilder ifTrueOrElse(boolean matchAll,
      Supplier<QueryBuilder> all,
      Supplier<QueryBuilder> other) {
    return matchAll ? all.get() : other.get();
  }

  private boolean noCategoryCriteria(SearchRequest request) {
    String[] categoryIds = request.getCategoryIds();
    return categoryIds == null || (categoryIds.length == 0);
  }

  private boolean noProducerCriteria(SearchRequest request) {
    int[] producerIds = request.getProducerIds();
    return producerIds == null || producerIds.length == 0;
  }
}
