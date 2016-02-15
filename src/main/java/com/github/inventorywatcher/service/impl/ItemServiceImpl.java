package com.github.inventorywatcher.service.impl;

import com.github.inventorywatcher.dao.ItemDao;
import com.github.inventorywatcher.model.Item;
import com.github.inventorywatcher.service.ItemService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

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
    public void getItems(Handler<AsyncResult<List<Item>>> handler) {
        dao.getItems(res -> {
            if (res.succeeded()) {
                handler.handle(Future.succeededFuture(res.result()));
            } else {
                handler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    @Override
    public void getItem(String id, Handler<AsyncResult<Item>> handler) {
        dao.getItem(id, res -> {
            if (res.succeeded()) {
                handler.handle(Future.succeededFuture(res.result()));
            } else {
                handler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    @Override
    public void createItem(Item item, Handler<AsyncResult<String>> handler) {
        List<String> errors = item.validate();
        if (errors.isEmpty()) {
            dao.createItem(item, res -> {
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
    public void updateItem(String id, Item item, Handler<AsyncResult<Void>> handler) {
        List<String> errors = item.validate();
        if (errors.isEmpty()) {
            dao.updateItem(id, item, res -> {
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
