package com.mak.loglly.vetx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class TestClass extends AbstractVerticle {
    
    final static String TOKEN = System.getProperty("TOKEN");
    
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new TestClass());

    }

    public void send(RoutingContext routingContext) {
  
       
        vertx.createHttpClient(new HttpClientOptions().setSsl(true))
                .post(443, "logs-01.loggly.com", TOKEN)
                .putHeader("Conent-Type", "text/plain")
                .handler(resp -> {})
                .end(routingContext.getBodyAsString());
        
        routingContext.response().setStatusCode(200).end();

    }

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        
        router.post("/send").handler(this::send);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

}
