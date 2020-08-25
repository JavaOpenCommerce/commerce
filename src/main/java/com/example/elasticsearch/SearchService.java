package com.example.elasticsearch;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import lombok.extern.jbosslog.JBossLog;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.enterprise.context.ApplicationScoped;

import java.util.function.Supplier;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@JBossLog
@ApplicationScoped
public class SearchService {

    private WebClient client;


    public SearchService(Vertx vertx, ElasticAddress address) {
        this.client = WebClient.create(vertx, new WebClientOptions()
                .setDefaultPort(address.getPort())
                .setDefaultHost(address.getHost()));
    }

    public Uni<JsonObject> searchItemsBySearchRequest(SearchRequest request) {
        SearchSourceBuilder ssb = new SearchSourceBuilder();

        String query = ssb.query(QueryBuilders.boolQuery()
                .must(ifTrueOrElse(isEmpty(request),
                        () -> matchAllQuery(),
                        () -> matchQuery("categoryIds", request.getCategoryId())))
                .must(ifTrueOrElse(isEmpty(request),
                        () -> matchAllQuery(),
                        () -> matchQuery("producerId", request.getProducerId())))
                .must(ifTrueOrElse(request.getSearchQuery() == null || request.getSearchQuery().isEmpty(),
                        () -> matchAllQuery(),
                        () -> matchQuery("details.name", request.getSearchQuery()))))
                .from(request.getPageSize() * request.getPageNum())
                .size(request.getPageSize())
                .toString();
        log.info(query);

        return client.get("/items/_search?filter_path=hits.hits._id,hits.total.value")
                .putHeader("Content-Length", "" + query.length())
                .putHeader("Content-Type", "application/json")
                .sendJsonObject(new JsonObject(query)).onItem().apply(resp -> {
                    if (resp.statusCode() == 200) {
                        return resp.bodyAsJsonObject();
                    } else {
                        return new JsonObject()
                                .put("code", resp.statusCode())
                                .put("message", resp.bodyAsString());
                    }
                });
    }

    private QueryBuilder ifTrueOrElse(Boolean matchAll,
                                      Supplier<QueryBuilder> all,
                                      Supplier<QueryBuilder> other) {
        return matchAll ? all.get() : other.get();
    }

    private boolean isEmpty(SearchRequest request) {
        return request.getCategoryId() == null || request.getCategoryId() == 0L;
    }
}
