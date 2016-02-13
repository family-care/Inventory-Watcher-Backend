package com.github.inventorywatcher.dao;

import com.github.inventorywatcher.dao.impl.ItemDaoImpl;
import com.github.inventorywatcher.model.Item;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;
//todo specify config object
public interface ItemDao {
    String COLLECTION = "items";

    static ItemDao create(Vertx vertx, JsonObject config) {
        return new ItemDaoImpl(vertx, config);
    }

    void getItems(Handler<AsyncResult<List<Item>>> handler);

    void getItem(String id, Handler<AsyncResult<Item>> handler);

    void createItem(Item item, Handler<AsyncResult<String>> handler);

    void updateItem(String id, Item item, Handler<AsyncResult<Void>> handler);

    void removeItem(String id, Handler<AsyncResult<Void>> handler);
}
