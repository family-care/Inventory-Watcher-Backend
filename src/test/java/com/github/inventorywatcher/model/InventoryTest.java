package com.github.inventorywatcher.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hunyadym on 2016. 02. 17..
 */
public class InventoryTest {


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
        String test = Json.encode(new Inventory("AAA", new ArrayList<>()));
        String expected = new JsonObject()
                .put("name", "AAA")
                .put("items", new JsonArray())
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojo() {
        String test = new JsonObject()
                .put("name", "AAA")
                .put("items", new JsonArray())
                .encode();
        String expected = Json.encode(new Inventory("AAA", new ArrayList<>()));
        assertEquals(expected, test);
    }

    @Test
    public void testValidateValidItem() {
        Inventory input = new Inventory("AAA", new ArrayList<>());
        List<String> errors = input.validate();
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateInvalidItem() {
        Inventory input = new Inventory();
        List<String> errors = input.validate();
        assertEquals(1, errors.size());
        assertTrue(errors.contains(Inventory.Message.getNAME_MUST_NOT_BE_NULL()));
    }

    @Test
    public void testJsonConvertable() {
        Inventory expected = new Inventory("AAA", new ArrayList<>());
        String json = expected.toJsonString();
        Inventory test = new Inventory(json);
        assertEquals(expected, test);
    }


}
