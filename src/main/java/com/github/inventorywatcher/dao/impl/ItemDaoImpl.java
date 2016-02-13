package com.github.inventorywatcher.dao.impl;

import com.github.inventorywatcher.dao.ItemDao;
import com.github.inventorywatcher.model.Item;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemDaoImpl implements ItemDao {

    private final MongoClient client;
    private final Vertx vertx;
//todo specify config object

    public ItemDaoImpl(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.client = MongoClient.createShared(vertx, config);
    }

    @Override
    public void getItems(Handler<AsyncResult<List<Item>>> handler) {
        client.find(COLLECTION, new JsonObject(), res
                -> handler.handle(
                        transformResult(res, jsons
                                -> jsons.stream().map(Item::new).collect(Collectors.toList()))
                )
        );
    }

    @Override
    public void getItem(String id, Handler<AsyncResult<Item>> handler) {
        client.findOne(COLLECTION, idObject(id), new JsonObject(), res -> {
            if(res.succeeded()){
                if(res.result()!=null){
                    handler.handle(transformResult(res, Item::new));
                }else{
                    handler.handle(Future.failedFuture("No item found with ID: "+id));
                }
            }else{
                handler.handle(Future.failedFuture(res.cause()));
            }
        }
        );
    }

    @Override
    public void createItem(Item item, Handler<AsyncResult<String>> handler) {
        //todo new JsonObject(Json.encode(item)) to item.toJsonObject()
        client.insert(COLLECTION, item.toJsonObject(), handler::handle);
    }

    @Override
    public void updateItem(String id, Item item, Handler<AsyncResult<Void>> handler) {
        JsonObject replacement = new JsonObject(Json.encode(item));
        client.replace(COLLECTION, idObject(id), replacement, handler::handle);
    }

    @Override
    public void removeItem(String id, Handler<AsyncResult<Void>> handler) {
        client.remove(COLLECTION, idObject(id), handler::handle);
    }

    private <T, U> AsyncResult<T> transformResult(AsyncResult<U> base, Function<U, T> transformer) {
        return new AsyncResult<T>() {
            @Override
            public T result() {
                return transformer.apply(base.result());
            }

            @Override
            public Throwable cause() {
                return base.cause();
            }

            @Override
            public boolean succeeded() {
                return base.succeeded();
            }

            @Override
            public boolean failed() {
                return base.failed();
            }
        };
    }

    private JsonObject idObject(String id) {
        return new JsonObject().put("_id", id);
    }
}
