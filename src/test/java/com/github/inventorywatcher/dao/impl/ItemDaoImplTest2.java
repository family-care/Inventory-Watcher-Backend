/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.inventorywatcher.dao.impl;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.inventorywatcher.MongoManager;
import com.github.inventorywatcher.PortProvider;
import com.github.inventorywatcher.dao.ItemDao;
import com.github.inventorywatcher.model.ItemJava;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Separate test file to test with an empty database
 * @author pjozsef
 */
@RunWith(VertxUnitRunner.class)
public class ItemDaoImplTest2 {

    private static final int MONGO_PORT = PortProvider.getNextPort();
    private static final JsonObject CONFIG = new JsonObject().put("host", "localhost").put("port", MONGO_PORT);
    private static MongoManager mongoManager;
    private static Vertx vertx;

    @BeforeClass
    public static void init(TestContext context) throws IOException, InterruptedException {
        Json.mapper.registerModule(new JavaTimeModule());
        vertx = Vertx.vertx();
        mongoManager = new MongoManager(vertx, MONGO_PORT, context, new ArrayList<>());//passing empty list
    }

    @AfterClass
    public static void shutdown(TestContext context) {
        mongoManager.stop();
    }

    @Test
    public void testGetItemsWithEmptyDB(TestContext context) {
        Async async = context.async();
        ItemDao dao = ItemDao.create(vertx, CONFIG);
        dao.getItems(res -> {
            context.assertTrue(res.succeeded());
            List<ItemJava> expected = new ArrayList<>();
            List<ItemJava> result = res.result();
            context.assertEquals(expected.size(), result.size());
            context.assertEquals(expected, result);
            async.complete();
        });
    }

    @Test
    public void testGetItemWithEmptyDB(TestContext context) {
        Async async = context.async();
        ItemDao dao = ItemDao.create(vertx, CONFIG);
        dao.getItem("#01", res -> {
            context.assertFalse(res.succeeded());
            async.complete();
        });
    }

    @Test
    public void testRemoveItemWithEmptyDB(TestContext context) {
        String id = "#03";
        Async async = context.async();
        ItemDao dao = ItemDao.create(vertx, CONFIG);
        dao.removeItem(id, res -> {
            //todo decide if success is OK if the document by ID does not exist
            //todo the same with update
            context.assertTrue(res.succeeded());
            async.complete();
        });
    }
}
