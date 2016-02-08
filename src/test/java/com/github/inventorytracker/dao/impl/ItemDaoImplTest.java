/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.inventorytracker.dao.impl;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.inventorytracker.MongoManager;
import com.github.inventorytracker.dao.ItemDao;
import com.github.inventorytracker.model.DateUnit;
import com.github.inventorytracker.model.Item;
import com.github.inventorytracker.model.JsonConvertable;
import com.github.inventorytracker.model.Notification;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author joci
 */
@RunWith(VertxUnitRunner.class)
public class ItemDaoImplTest {

    private static int MONGO_PORT = 12345;
    private static MongoManager mongoManager;
    private static JsonObject config = new JsonObject().put("host", "localhost").put("port", MONGO_PORT);
    private static Vertx vertx;

    @BeforeClass
    public static void init(TestContext context) throws IOException, InterruptedException {
        Json.mapper.registerModule(new JavaTimeModule());
        vertx = Vertx.vertx();
        mongoManager = new MongoManager(vertx, MONGO_PORT, context, getData());
    }

    @AfterClass
    public static void shutdown(TestContext context) {
        mongoManager.stop();
    }

    @Test
    public void testGetItems(TestContext context) {
        Async async = context.async();
        ItemDao dao = ItemDao.create(vertx, config);
        dao.getItems(res -> {
            context.assertTrue(res.succeeded());
            System.out.println(res.result());
            Set<Item> expected = new HashSet(getData());
            Set<Item> result = new HashSet(res.result());
            context.assertEquals(expected.size(), result.size());
            context.assertEquals(expected, result);
            async.complete();
        });
    }
    
    private static List<? extends JsonConvertable> getData() {
        return Arrays.asList(
                Item.builder()
                ._id("#01")
                .name("rice")
                .quantity(5)
                .unit("kg")
                .build(),
                Item.builder()
                ._id("#02")
                .name("apple")
                .quantity(1)
                .build(),
                Item.builder()
                ._id("#03")
                .name("pasta")
                .quantity(2)
                .unit("kg")
                .bestBefore(LocalDate.now().plusDays(20))
                .notification(
                        Notification.builder()
                        .on(LocalDate.now().plusWeeks(2))
                        .repeatInterval(1)
                        .unit(DateUnit.DAY).build())
                .build()
        );
    }
}
