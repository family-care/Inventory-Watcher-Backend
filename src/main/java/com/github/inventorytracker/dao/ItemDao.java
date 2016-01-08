package com.github.inventorytracker.dao;

import com.github.inventorytracker.dao.impl.ItemDaoImpl;
import com.github.inventorytracker.model.Item;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

public interface ItemDao {
    static ItemDao getInstance(Vertx vertx){
        return new ItemDaoImpl(vertx);
    }

    void getItems(Handler<AsyncResult<List<Item>>> handler);
    void getItem(String id, Handler<AsyncResult<Item>> handler);
    void createItem(Item item, Handler<AsyncResult<String>> handler);
    void updateItem(String id, Item item, Handler<AsyncResult<Void>> handler);
    void removeItem(String id, Handler<AsyncResult<Void>> handler);
}
