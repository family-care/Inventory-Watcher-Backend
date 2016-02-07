package com.github.inventorytracker.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class NotificationTest {

    @BeforeClass
    public static void init() {
        Json.mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testPojoToString() {
        String test = Json.encode(
                Notification.builder()
                .on(LocalDate.parse("2007-12-03"))
                .repeatInterval(3)
                .unit(DateUnit.DAY)
                .build());
        String expected = new JsonObject()
                .put("on", new JsonArray("[2007,12,3]"))
                .put("repeatInterval", 3)
                .put("unit", "DAY")
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojo() {
        Notification test = Json.decodeValue(
                new JsonObject()
                .put("on", new JsonArray("[2007,12,3]"))
                .put("repeatInterval", 3)
                .put("unit", "DAY")
                .encode(),
                Notification.class);
        Notification expected = Notification.builder()
                .on(LocalDate.parse("2007-12-03"))
                .repeatInterval(3)
                .unit(DateUnit.DAY)
                .build();
        assertEquals(expected, test);
    }

    @Test
    public void testJsonArgConstructor() {
        Notification test = new Notification(
                new JsonObject()
                .put("on", new JsonArray("[2007,12,3]"))
                .put("repeatInterval", 3)
                .put("unit", "DAY")
        );
        Notification expected = Notification.builder()
                .on(LocalDate.parse("2007-12-03"))
                .repeatInterval(3)
                .unit(DateUnit.DAY)
                .build();
        assertEquals(expected, test);
    }
}
//todo test validate method
