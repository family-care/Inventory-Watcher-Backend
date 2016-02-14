package com.github.inventorywatcher.service.impl;

import com.github.inventorywatcher.dao.ItemDao;
import com.github.inventorywatcher.model.ItemJava;
import com.github.inventorywathcer.model.Item;
import com.github.inventorywatcher.service.ItemService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pjozsef
 */
public class ItemServiceImpl implements ItemService {
    private final Vertx vertx;
    private final ItemDao dao;

    //todo specify config object
    public ItemServiceImpl(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        dao = ItemDao.create(vertx, config);
    }

    @Override
    public void getItems(Handler<AsyncResult<List<ItemJava>>> handler) {
        dao.getItems(res -> {
            if (res.succeeded()) {
                handler.handle(Future.succeededFuture(res.result()));
            } else {
                handler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    @Override
    public void getItem(String id, Handler<AsyncResult<ItemJava>> handler) {
        dao.getItem(id, res -> {
            if (res.succeeded()) {
                handler.handle(Future.succeededFuture(res.result()));
            } else {
                handler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    @Override
    public void createItem(ItemJava itemJava, Handler<AsyncResult<String>> handler) {
        Item item2 = new Item("fds", "321", "3213", 23.0d, "sss", null, new ArrayList<>(), null);

        List<String> errors = itemJava.validate();
        if (errors.isEmpty()) {
            dao.createItem(itemJava, res -> {
                if (res.succeeded()) {
                    handler.handle(Future.succeededFuture(res.result()));
                } else {
                    handler.handle(Future.failedFuture(res.cause()));
                }
            });
        } else {
            handler.handle(Future.failedFuture(String.join("\n", errors)));
        }
    }

    @Override
    public void updateItem(String id, ItemJava itemJava, Handler<AsyncResult<Void>> handler) {
        List<String> errors = itemJava.validate();
        if (errors.isEmpty()) {
            dao.updateItem(id, itemJava, res -> {
                if (res.succeeded()) {
                    handler.handle(Future.succeededFuture(res.result()));
                } else {
                    handler.handle(Future.failedFuture(res.cause()));
                }
            });
        } else {
            handler.handle(Future.failedFuture(String.join("\n", errors)));
        }
    }

    @Override
    public void removeItem(String id, Handler<AsyncResult<Void>> handler) {
        dao.removeItem(id, res -> {
            if (res.succeeded()) {
                handler.handle(Future.succeededFuture(res.result()));
            } else {
                handler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

}
