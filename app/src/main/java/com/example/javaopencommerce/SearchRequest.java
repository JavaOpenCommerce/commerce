package com.example.javaopencommerce;

import javax.ws.rs.DefaultValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

// TODO would be better to remove this restEasy dependency from here !

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @QueryParam("pageNum")
    @DefaultValue("0")
    private int pageNum;

    @QueryParam("pageSize")
    @DefaultValue("10")
    private int pageSize;

    @QueryParam("categoryId")
    @DefaultValue("0")
    private Long categoryId;

    @QueryParam("producerId")
    @DefaultValue("0")
    private Long producerId;

    @QueryParam("order")
    @DefaultValue("ASC")
    private String order;

    @QueryParam("sortBy")
    @DefaultValue("name")
    private String sortBy;

    @QueryParam("searchQuery")
    @DefaultValue("")
    private String searchQuery;
}
