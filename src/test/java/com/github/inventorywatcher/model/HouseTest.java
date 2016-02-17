package com.github.inventorywatcher.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hunyadym on 2016. 02. 17..
 */
public class HouseTest {


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
        String test = Json.encode(new House(null, "AAA", new LinkedList<>(), new LinkedList<>()));
        String expected = new JsonObject()
                .put("name", "AAA")
                .put("users", new JsonArray())
                .put("inventory", new JsonArray())
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojo() {
        String test = new JsonObject()
                .put("name", "AAA")
                .put("users", new JsonArray())
                .put("inventory", new JsonArray())
                .encode();
        String expected = Json.encode(new House(null, "AAA", new LinkedList<>(), new LinkedList<>()));
        assertEquals(expected, test);
    }

    @Test
    public void testValidateValidItem() {
        House input = new House(null, "AAA", new LinkedList<>(), new LinkedList<>());
        List<String> errors = input.validate();
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateInvalidItem() {
        House input = new House();
        List<String> errors = input.validate();
        assertEquals(1, errors.size());
        assertTrue(errors.contains(Item.Message.getNAME_MUST_NOT_BE_NULL()));
    }

    @Test
    public void testJsonConvertable() {
        House expected = new House(null, "AAA", new LinkedList<>(), new LinkedList<>());
        String json = expected.toJsonString();
        House test = new House(json);
        assertEquals(expected, test);
    }


}
