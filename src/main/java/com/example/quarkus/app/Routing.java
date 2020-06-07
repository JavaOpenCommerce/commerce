package com.example.quarkus.app;


import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Routing {

    @Route(path="/hello", methods = HttpMethod.GET)
    void hello(RoutingContext rc) {
        rc.response().end("hello");
    }

    @Route(path = "/greetings", methods = HttpMethod.GET)
    void greetings(RoutingExchange ex) {
        ex.ok("hello " + ex.getParam("name").orElse("world"));
    }
}
