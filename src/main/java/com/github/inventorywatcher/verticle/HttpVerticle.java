package com.github.inventorywatcher.verticle;

import com.github.inventorywatcher.model.Item;
import com.github.inventorywatcher.service.ItemService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.serviceproxy.ProxyHelper;

public class HttpVerticle extends AbstractVerticle {
    ItemService itemService;

    @Override
    public void start() {
        itemService = ProxyHelper.createProxy(
                ItemService.class,
                vertx,
                ItemService.ADDRESS);
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route("/get").handler(cb -> {
            HttpServerResponse response = cb.response();
            itemService.getItems(res ->{
                if(res.succeeded()){
                    response.end(Json.encode(res.result()));
                }else{
                    response.end(res.cause().getMessage());
                }
            });
        });

        router.route("/get/:id").handler(ctx -> {
            HttpServerResponse res = ctx.response();
            HttpServerRequest req = ctx.request();
            itemService.getItem(req.getParam("id"), cb -> {
                if(cb.succeeded()){
                    res.end(cb.result().toJsonString());
                }else{
                    res.end(cb.cause().getMessage());
                }
            });
        });

        router.route("/add").handler(ctx -> {
            HttpServerResponse res = ctx.response();
            Item item = new Item(null, "dummy", null, Math.random()*200, null, null, null, null);
            itemService.createItem(item, cb -> {
                if(cb.succeeded()){
                    res.end(cb.result());
                }else{
                    res.end(cb.cause().getMessage());
                }
            });
        });

        router.route("/remove/:id").handler(ctx -> {
            HttpServerResponse res = ctx.response();
            String id = ctx.request().getParam("id");
            itemService.removeItem(id, cb -> {
                if(cb.succeeded()){
                    res.end(id);
                }else{
                    res.end(cb.cause().getMessage());
                }
            });
        });


        server.requestHandler(router::accept);
        server.listen(8080, "0.0.0.0");
    }
}
