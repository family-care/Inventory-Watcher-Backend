package com.github.inventorywatcher.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemJavaTest {
//todo test validate method

    @BeforeClass
    public static void setUpClass() {
        Json.mapper.registerModule(new JavaTimeModule());
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
        String test = Json.encode(new ItemJava(null, "AAA", null, 20, "kg", null, new ArrayList<String>(), null));
        String expected = new JsonObject()
                .put("name", "AAA")
                .put("quantity", 20.0)
                .put("unit", "kg")
                .put("tags", new JsonArray())
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testPojoToStringWithNotification() {
        String test = Json.encode(new ItemJava(null, "AAA", "012345", 20, "kg", null, new ArrayList<String>(), new Notification(LocalDate.parse("2007-12-03"), 2, DateUnit.DAY)));
        String expected = new JsonObject()
                .put("name", "AAA")
                .put("barcode", "012345")
                .put("quantity", 20.0)
                .put("unit", "kg")
                .put("tags", new JsonArray())
                .put("notification",
                        new JsonObject()
                        .put("on", new JsonArray("[2007,12,3]"))
                        .put("repeatInterval", 2)
                        .put("unit", "DAY"))
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojo() {
        ItemJava test = Json.decodeValue(
                new JsonObject()
                .put("name", "AAA")
                .put("quantity", 20)
                .put("unit", "kg")
                .put("tags", new JsonArray())
                .encode(),
                ItemJava.class);
        ItemJava expected = new ItemJava(null, "AAA", null, 20, "kg", null, new ArrayList<String>(), null);
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojoWithNotification() {
        ItemJava test = Json.decodeValue(
                new JsonObject()
                .put("name", "AAA")
                .put("quantity", 20)
                .put("unit", "kg")
                .put("tags", new JsonArray())
                .put("notification",
                        new JsonObject()
                        .put("on", new JsonArray("[2007,12,3]"))
                        .put("repeatInterval", 2)
                        .put("unit", "DAY"))
                .encode(),
                ItemJava.class);
        ItemJava expected = new ItemJava(null, "AAA", null, 20, "kg", null, new ArrayList<String>(), new Notification(LocalDate.parse("2007-12-03"), 2, DateUnit.DAY));
        assertEquals(expected, test);
    }

    @Test
    public void testJsonArgConstructor() {
        ItemJava test = new ItemJava(
                new JsonObject()
                .put("name", "AAA")
                .put("quantity", 20)
                .put("unit", "kg")
                .put("tags", new JsonArray())
        );
        ItemJava expected = new ItemJava(null, "AAA", null, 20, "kg", null, new ArrayList<String>(), null);
        assertEquals(expected, test);
    }

    @Test
    public void testValidateValidItem() {
        ItemJava input = new ItemJava(null, "AAA", "012345", 0, null, null, null, null);
        List<String> errors = input.validate();
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateInvalidItem() {
        ItemJava input = new ItemJava();
        List<String> errors = input.validate();
        assertEquals(1, errors.size());
        assertTrue(errors.contains(ItemJava.NAME_MUST_NOT_BE_NULL));
    }

    @Test
    public void testJsonConvertable() {
        ItemJava expected = new ItemJava(null, "AAA", "012345", 20, "kg", null, null, new Notification(LocalDate.parse("2007-12-03"), 2, DateUnit.DAY));
        String json = expected.toJsonString();
        ItemJava test = new ItemJava(json);
        assertEquals(expected, test);
    }
}
