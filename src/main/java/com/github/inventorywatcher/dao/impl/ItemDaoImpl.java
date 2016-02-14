package com.github.inventorywatcher.dao.impl;

import com.github.inventorywatcher.dao.ItemDao;
import com.github.inventorywatcher.model.Item;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
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
                    handler.handle(Future.failedFuture(NO_ITEM_FOUND_WITH_ID+id));
                }
            }else{
                handler.handle(Future.failedFuture(res.cause()));
            }
        }
        );
    }

    @Override
    public void createItem(Item item, Handler<AsyncResult<String>> handler) {
        if(item.get_id()!=null){//todo decide if this logic  belongs to the service or dao level and update tests in dao and service accordingly
            handler.handle(Future.failedFuture(ID_MUST_BE_NULL+item));
        }else{
            client.insert(COLLECTION, item.toJson(), handler::handle);
        }
    }

    @Override
    public void updateItem(String id, Item item, Handler<AsyncResult<Void>> handler) {
        client.replace(COLLECTION, idObject(id), item.toJson(), res -> {
            handler.handle(res);
        });
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
