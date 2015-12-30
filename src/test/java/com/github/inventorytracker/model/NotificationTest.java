package com.github.inventorytracker.model;


import io.vertx.core.json.JsonObject;
import org.junit.*;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;


public class NotificationTest {


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
    public void testToJsonNoRepeat() {
        Notification base = Notification.builder().on(LocalDate.now()).build();
        JsonObject expected = new JsonObject().put(Notification.ON, LocalDate.now().toString());
        assertEquals(expected, base.toJson());
        assertEquals(expected, Notification.toJson(base));
    }

    @Test
    public void testToJsonWithRepeat() {
        Notification base = Notification.builder()
                .on(LocalDate.now())
                .repeatInterval(3)
                .unit(DateUnit.WEEK)
                .build();
        JsonObject expected = new JsonObject()
                .put(Notification.ON, LocalDate.now().toString())
                .put(Notification.REPEAT_INTERVAL, 3)
                .put(Notification.UNIT, "WEEK");
        assertEquals(expected, base.toJson());
        assertEquals(expected, Notification.toJson(base));
    }

    @Test
    public void testFromJsonNoRepeat() {
        JsonObject base = new JsonObject().put(Notification.ON, LocalDate.now().toString());
        Notification expected = Notification.builder().on(LocalDate.now()).build();
        assertEquals(expected, Notification.fromJson(base));
    }

    @Test
    public void testFromJsonWithRepeat() {
        JsonObject base = new JsonObject()
                .put(Notification.ON, LocalDate.now().toString())
                .put(Notification.REPEAT_INTERVAL, 3)
                .put(Notification.UNIT, "WEEK");
        Notification expected = Notification.builder()
                .on(LocalDate.now())
                .repeatInterval(3)
                .unit(DateUnit.WEEK)
                .build();
        assertEquals(expected, Notification.fromJson(base));
    }
}


