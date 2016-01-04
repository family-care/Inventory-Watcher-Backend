package com.github.inventorytracker.model;


import com.github.inventorytracker.verticle.util.JsonUtil;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.*;

import static org.junit.Assert.assertEquals;


public class ItemTest {


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPojoToString() {
        String test = JsonUtil.toJson(
                Item.builder()
                        .name("AAA")
                        .quantity(20)
                        .unit("kg")
                        .build());
        String expected = new JsonObject()
                .put("name", "AAA")
                .put("quantity", 20)
                .put("unit", "kg")
                .putNull("bestBefore")
                .put("tags", new JsonArray())
                .putNull("notification")
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojo() {
        Item test = JsonUtil.fromJson(
                new JsonObject()
                        .put("name", "AAA")
                        .put("quantity", 20)
                        .put("unit", "kg")
                        .put("tags", new JsonArray())
                        .encode(),
                Item.class);
        Item expected = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .build();
        assertEquals(expected, test);
    }
}


