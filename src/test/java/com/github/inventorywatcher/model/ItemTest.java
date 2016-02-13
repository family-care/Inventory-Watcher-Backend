package com.github.inventorywatcher.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.time.LocalDate;
import java.util.List;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemTest {
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
        String test = Json.encode(
                Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .build());
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
        String test = Json.encode(
                Item.builder()
                .name("AAA")
                .barcode("012345")
                .quantity(20)
                .unit("kg")
                .notification(
                        Notification.builder()
                        .on(LocalDate.parse("2007-12-03"))
                        .repeatInterval(2)
                        .unit(DateUnit.DAY)
                        .build())
                .build());
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
        Item test = Json.decodeValue(
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

    @Test
    public void testStringToPojoWithNotification() {
        Item test = Json.decodeValue(
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
                Item.class);
        Item expected = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .notification(
                        Notification.builder()
                        .on(LocalDate.parse("2007-12-03"))
                        .repeatInterval(2)
                        .unit(DateUnit.DAY)
                        .build())
                .build();
        assertEquals(expected, test);
    }

    @Test
    public void testJsonArgConstructor() {
        Item test = new Item(
                new JsonObject()
                .put("name", "AAA")
                .put("quantity", 20)
                .put("unit", "kg")
                .put("tags", new JsonArray())
        );
        Item expected = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .build();
        assertEquals(expected, test);
    }

    @Test
    public void testValidateValidItem() {
        Item input = Item.builder().name("AAA").barcode("012345").build();
        List<String> errors = input.validate();
        assertTrue(errors.isEmpty());
    }
    
    @Test
    public void testValidateInvalidItem(){
        Item input = new Item();
        List<String> errors = input.validate();
        assertEquals(1, errors.size());
        assertTrue(errors.contains(Item.NAME_MUST_NOT_BE_NULL));
    }
    
    @Test
    public void testJsonConvertable(){
        Item expected = Item.builder()
                .name("AAA")
                .quantity(20)
                .unit("kg")
                .notification(
                        Notification.builder()
                        .on(LocalDate.parse("2007-12-03"))
                        .repeatInterval(2)
                        .unit(DateUnit.DAY)
                        .build())
                .build();
        String json = expected.toJson();
        Item test = new Item(json);
        assertEquals(expected, test);
    }
}
