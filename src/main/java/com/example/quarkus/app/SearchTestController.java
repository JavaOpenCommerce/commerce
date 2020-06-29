package com.example.quarkus.app;

import com.example.elasticsearch.SearchRequest;
import com.example.elasticsearch.SearchService;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/elastic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchTestController {

    private final SearchService searchService;

    public SearchTestController(SearchService searchService) {this.searchService = searchService;}

    @GET
    @Path("/items")
    public String search(@BeanParam SearchRequest request) {
        searchService.searchItemsByCategoryNProducer(request);
        return "OK";
    }

}
