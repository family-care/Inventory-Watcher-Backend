package com.github.inventorywatcher.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hunyadym on 2016. 02. 17..
 */
public class MemberTest {


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
        String test = Json.encode(new Member(null, "AAA"));
        String expected = new JsonObject()
                .put("right", "AAA")
                .encode();
        assertEquals(expected, test);
    }

    @Test
    public void testStringToPojo() {
        String test = new JsonObject()
                .put("right", "AAA")
                .encode();
        String expected = Json.encode(new Member(null, "AAA"));
        assertEquals(expected, test);
    }

    @Test
    public void testValidateValidItem() {
        Member input = new Member("123", "AAA");
        List<String> errors = input.validate();
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateInvalidItem() {
        Member input = new Member();
        List<String> errors = input.validate();
        assertEquals(1, errors.size());
        assertTrue(errors.contains(Member.Message.getID_MUST_NOT_BE_NULL()));
    }

    @Test
    public void testJsonConvertable() {
        Member expected = new Member(null, "AAA");
        String json = expected.toJsonString();
        Member test = new Member(json);
        assertEquals(expected, test);
    }


}
