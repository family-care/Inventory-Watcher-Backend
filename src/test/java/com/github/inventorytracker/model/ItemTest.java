package com.github.inventorytracker.model;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void testToJson_someFieldsAbsent() {
        Item base = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .build();
        JsonObject expected = new JsonObject()
                .put(Item.NAME, "AAA")
                .put(Item.QUANTITY, 20)
                .put(Item.UNIT, "kg")
                .put(Item.TAGS, new JsonArray());
        assertEquals(expected, base.toJson());
        assertEquals(expected, Item.toJson(base));
    }

    @Test
    public void testToJson_allFieldsPresent() {
        LocalDate now = LocalDate.now();

        Notification notification = Notification.builder()
                .on(now.plus(2, ChronoUnit.DAYS))
                .build();

        Item base = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .bestBefore(now)
                .tag("high quality")
                .tag("fine")
                .notification(notification)
                .build();

        JsonObject expected = new JsonObject()
                .put(Item.NAME, "AAA")
                .put(Item.QUANTITY, 20)
                .put(Item.UNIT, "kg")
                .put(Item.BEST_BEFORE, now.toString())
                .put(Item.TAGS, new JsonArray(Arrays.asList("high quality", "fine")))
                .put(Item.NOTIFICATION, notification.toJson());
        assertEquals(expected, base.toJson());
        assertEquals(expected, Item.toJson(base));
    }

    @Test
    public void testFromJson_someFieldsAbsent() {
        JsonObject base = new JsonObject()
                .put(Item.NAME, "AAA")
                .put(Item.QUANTITY, 20)
                .put(Item.UNIT, "kg");
        Item expected = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .tags(new ArrayList<>()).build();
        assertEquals(expected, Item.fromJson(base));
    }

    @Test
    public void testFromJson_allFieldsPresent() {
        LocalDate now = LocalDate.now();

        Notification notification = Notification.builder()
                .on(now.plus(2, ChronoUnit.DAYS))
                .build();

        JsonObject base = new JsonObject()
                .put(Item.NAME, "AAA")
                .put(Item.QUANTITY, 20)
                .put(Item.UNIT, "kg")
                .put(Item.BEST_BEFORE, now.toString())
                .put(Item.TAGS, new JsonArray(Arrays.asList("high quality", "fine")))
                .put(Item.NOTIFICATION, notification.toJson());

        Item expected = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .bestBefore(now)
                .tag("high quality")
                .tag("fine")
                .notification(notification)
                .build();

        assertEquals(expected, Item.fromJson(base));
    }
}


