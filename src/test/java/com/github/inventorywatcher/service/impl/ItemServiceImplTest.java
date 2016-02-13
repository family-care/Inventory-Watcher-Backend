package com.github.inventorywatcher.service.impl;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.inventorywatcher.MongoManager;
import com.github.inventorywatcher.model.DateUnit;
import com.github.inventorywatcher.model.Item;
import com.github.inventorywatcher.model.JsonConvertable;
import com.github.inventorywatcher.model.Notification;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

/**
 *
 * @author pjozsef
 */
@RunWith(VertxUnitRunner.class)
public class ItemServiceImplTest {
    
    private static final int MONGO_PORT = 101010;
    private static MongoManager mongoManager;
    private static final JsonObject CONFIG = new JsonObject().put("host", "localhost").put("port", MONGO_PORT);
    private static Vertx vertx;

    @BeforeClass
    public static void setUpClass(TestContext context) throws IOException {
        Json.mapper.registerModule(new JavaTimeModule());
        vertx = Vertx.vertx();
        mongoManager = new MongoManager(vertx, MONGO_PORT, context, getData());
    }
    
    @AfterClass
    public static void tearDownClass() {
        mongoManager.stop();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getItems method, of class ItemServiceImpl.
     */
    @Test
    public void testGetItems() {
        System.out.println("getItems");
        Handler<AsyncResult<List<Item>>> handler = null;
        ItemServiceImpl instance = null;
        instance.getItems(handler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getItem method, of class ItemServiceImpl.
     */
    @Test
    public void testGetItem() {
        System.out.println("getItem");
        String id = "";
        Handler<AsyncResult<Item>> handler = null;
        ItemServiceImpl instance = null;
        instance.getItem(id, handler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createItem method, of class ItemServiceImpl.
     */
    @Test
    public void testCreateItem() {
        System.out.println("createItem");
        Item item = null;
        Handler<AsyncResult<String>> handler = null;
        ItemServiceImpl instance = null;
        instance.createItem(item, handler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateItem method, of class ItemServiceImpl.
     */
    @Test
    public void testUpdateItem() {
        System.out.println("updateItem");
        String id = "";
        Item item = null;
        Handler<AsyncResult<Void>> handler = null;
        ItemServiceImpl instance = null;
        instance.updateItem(id, item, handler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeItem method, of class ItemServiceImpl.
     */
    @Test
    public void testRemoveItem() {
        System.out.println("removeItem");
        String id = "";
        Handler<AsyncResult<Void>> handler = null;
        ItemServiceImpl instance = null;
        instance.removeItem(id, handler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
