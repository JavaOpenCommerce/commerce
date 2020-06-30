package com.example.elasticsearch;

import lombok.Getter;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.DefaultValue;

@Getter
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

    @QueryParam("sort")
    @DefaultValue("id")
    private String sortBy;


}
