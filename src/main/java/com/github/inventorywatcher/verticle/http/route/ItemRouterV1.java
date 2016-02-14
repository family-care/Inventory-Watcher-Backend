package com.github.inventorywatcher.verticle.http.route;

import com.github.inventorywatcher.model.ItemJava;
import com.github.inventorywatcher.service.ItemService;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ProxyHelper;

public class ItemRouterV1 {
    private final ItemService itemService;
    private final Vertx vertx;

    public ItemRouterV1(Vertx vertx) {
        this.vertx = vertx;
        itemService = ProxyHelper.createProxy(ItemService.class, vertx, ItemService.ADDRESS);
    }
    //todo use kotlin Item instead of java version

    public Router get() {
        final Router sub = Router.router(vertx);
        sub.get("items").handler(this::getAll);
        sub.get("items/:id").handler(this::get);
        sub.post("items").handler(this::post);
        sub.put("items/:id").handler(this::put);
        sub.delete("items/:id").handler(this::delete);
        return sub;
    }

    private void getAll(RoutingContext ctx) {
        HttpServerResponse response = ctx.response();
        itemService.getItems(res -> {
            if (res.succeeded()) {
                response.end(Json.encode(res.result()));
            } else {
                response.end(res.cause().getMessage());
            }
        });
    }
    private void get(RoutingContext ctx) {
        HttpServerResponse res = ctx.response();
        HttpServerRequest req = ctx.request();
        itemService.getItem(req.getParam("id"), cb -> {
            if (cb.succeeded()) {
                res.end(cb.result().toJsonString());
            } else {
                res.end(cb.cause().getMessage());
            }
        });
    }
    private void post(RoutingContext ctx) {//todo this is a dummy implementation
        HttpServerResponse res = ctx.response();
        ItemJava itemJava = new ItemJava(null, "dummy", null, Math.random() * 200, null, null, null, null);
        itemService.createItem(itemJava, cb -> {
            if (cb.succeeded()) {
                res.end(cb.result());
            } else {
                res.end(cb.cause().getMessage());
            }
        });
    }
    private void put(RoutingContext ctx) {
        //todo implement me
    }

    private void delete(RoutingContext ctx) {
        HttpServerResponse res = ctx.response();
        String id = ctx.request().getParam("id");
        itemService.removeItem(id, cb -> {
            if (cb.succeeded()) {
                res.end(id);
            } else {
                res.end(cb.cause().getMessage());
            }
        });
    }
}
