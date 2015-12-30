package com.github.inventorytracker.dao;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class ItemDao {
    private MongoClient client;
    private Vertx vertx;

    public ItemDao(Vertx vertx){
        this.vertx=vertx;
        this.client = MongoClient.createShared(vertx, new JsonObject());
    }


}
