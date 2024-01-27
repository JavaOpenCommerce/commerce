package com.example.opencommerce.adapters.database.catalog.elastic;

import com.example.opencommerce.app.catalog.query.ItemSearchService;
import com.example.opencommerce.app.catalog.query.SearchRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.function.Supplier;


@Slf4j
@ApplicationScoped
class SearchService implements ItemSearchService {

    private final WebClient client;

    SearchService(@ElasticSearchClient WebClient client) {
        this.client = client;
    }

    public JsonObject searchProductsBySearchRequest(SearchRequest request) {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        String query = ssb.query(QueryBuilders.boolQuery()
                        .must(ifTrueOrElse(request.getPriceMin() == null,
                                QueryBuilders::matchAllQuery,
                                () -> QueryBuilders.rangeQuery("valueGross")
                                        .gte(request.getPriceMin())))
                        .must(ifTrueOrElse(request.getPriceMax() == null,
                                QueryBuilders::matchAllQuery,
                                () -> QueryBuilders.rangeQuery("valueGross")
                                        .lte(request.getPriceMax())))
                        .must(ifTrueOrElse(noCategoryCriteria(request),
                                QueryBuilders::matchAllQuery,
                                () -> QueryBuilders.termsQuery("categoryIds.keyword", request.getCategoryIds())))
                        .must(ifTrueOrElse(noProducerCriteria(request),
                                QueryBuilders::matchAllQuery,
                                () -> QueryBuilders.termsQuery("producerId", request.getProducerIds())))
                        .must(ifTrueOrElse(request.getSearchQuery() == null || request.getSearchQuery()
                                        .isEmpty(),
                                QueryBuilders::matchAllQuery,
                                () -> QueryBuilders.multiMatchQuery(request.getSearchQuery(), "name",
                                        "description"))))
                .from(request.getPageSize() * request.getPageNum())
                .size(request.getPageSize())
                .toString();
        log.info(query);

        HttpResponse<Buffer> resp = client.get(
                        "/items/_search?filter_path=hits.hits._id,hits.total.value")
                .putHeader("Content-Length", String.valueOf(query.length()))
                .putHeader("Content-Type", "application/json")
                .sendJsonObject(new JsonObject(query))
                .await()
                .indefinitely();

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
