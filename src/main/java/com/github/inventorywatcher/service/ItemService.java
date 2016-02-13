package com.github.inventorywatcher.service;

import com.github.inventorywatcher.model.Item;
import com.github.inventorywatcher.service.impl.ItemServiceImpl;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.List;

/**
 * @author pjozsef
 */
@ProxyGen
public interface ItemService {
    String ADDRESS = "com.github.inventorywatcher.service.ItemService.EventBusAddress";
    //todo specify config object
    static ItemService create(Vertx vertx, JsonObject config) {
        return new ItemServiceImpl(vertx, config);
    }

    static ItemService createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(ItemService.class, vertx, address);
    }

    void getItems(Handler<AsyncResult<List<Item>>> handler);

    void getItem(String id, Handler<AsyncResult<Item>> handler);

    void createItem(Item item, Handler<AsyncResult<String>> handler);

    void updateItem(String id, Item item, Handler<AsyncResult<Void>> handler);

    void removeItem(String id, Handler<AsyncResult<Void>> handler);
}
