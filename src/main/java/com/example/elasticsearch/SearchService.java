package com.example.elasticsearch;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import lombok.extern.jbosslog.JBossLog;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.enterprise.context.ApplicationScoped;

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

    public void searchItemsByCategoryNProducer(SearchRequest request) {

        SearchSourceBuilder ssb = new SearchSourceBuilder();

        String query = ssb.query(QueryBuilders.boolQuery()
                .mustNot(matchQuery("category.categoryName", "Shipping"))
                .must(request.getCategory() == null || request.getCategory().trim().isEmpty() ?
                        matchAllQuery() :
                        matchQuery("category.categoryName", request.getCategory()))
                .must(request.getProducer() == null || request.getProducer().trim().isEmpty() ?
                        matchAllQuery() :
                        matchQuery("producer.name", request.getProducer())))
                .sort(new FieldSortBuilder(request.getSortBy()).order(SortOrder.valueOf(request.getOrder().toUpperCase())))
                .from(request.getPageSize()*request.getPageNum())
                .size(request.getPageSize())
                .toString();

        log.info(query);

        client.get("/items/_search?filter_path=hits.hits._id")
                .putHeader("Content-Length", "" + query.length())
                .putHeader("Content-Type", "application/json")
                .sendJsonObject(new JsonObject(query), ar -> ar.result()
                        .bodyAsJsonObject()
                        .getJsonObject("hits")
                        .getJsonArray("hits").forEach(log::info));
    }
}
