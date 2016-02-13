package com.github.inventorywatcher.service.impl;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.inventorywatcher.MongoManager;
import com.github.inventorywatcher.PortProvider;
import com.github.inventorywatcher.dao.ItemDao;
import com.github.inventorywatcher.model.DateUnit;
import com.github.inventorywatcher.model.Item;
import com.github.inventorywatcher.model.JsonConvertable;
import com.github.inventorywatcher.model.Notification;
import com.github.inventorywatcher.service.ItemService;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pjozsef
 */
@RunWith(VertxUnitRunner.class)
public class ItemServiceImplTest {

    private static final int MONGO_PORT = PortProvider.getNextPort();
    private static final JsonObject CONFIG = new JsonObject().put("host", "localhost").put("port", MONGO_PORT);
    private static MongoManager mongoManager;
    private static Vertx vertx;

    private ItemService service;

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
        service = new ItemServiceImpl(vertx, CONFIG);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getItems method, of class ItemServiceImpl.
     */
    @Test
    public void testGetItems(TestContext context) {
        Async async = context.async();
        service.getItems(res -> {
            context.assertTrue(res.succeeded());
            Set<JsonConvertable> expected = new HashSet<>(getData());
            Set<JsonConvertable> result = new HashSet<>(res.result());
            context.assertEquals(expected.size(), result.size());
            context.assertEquals(expected, result);
            async.complete();
        });
    }

    /**
     * Test of getItem method, of class ItemServiceImpl.
     */
    @Test
    public void testGetItem(TestContext context) {
        Async async = context.async();
        String id = "#01";
        service.getItem(id, res -> {
            context.assertTrue(res.succeeded());
            Item expected = Item.builder()
                    ._id("#01")
                    .name("rice")
                    .quantity(5)
                    .unit("kg")
                    .build();
            Item test = res.result();
            context.assertEquals(expected, test);
            async.complete();
        });
    }

    /**
     * Test of createItem method, of class ItemServiceImpl.
     */
    @Test
    public void testCreateItem(TestContext context) {
        Item expected = Item.builder()
                .name("test")
                .quantity(15)
                .unit("kg")
                .barcode("01024150")
                .bestBefore(LocalDate.parse("2007-10-12"))
                .notification(Notification.builder().on(LocalDate.parse("2010-05-12")).build())
                .build();
        Async async = context.async();
        service.createItem(expected, res -> {
            context.assertTrue(res.succeeded());
            String id = res.result();
            service.getItem(id, res2 -> {
                context.assertTrue(res2.succeeded());
                Item test = res2.result();
                expected.set_id(id);
                context.assertEquals(expected, test);
                async.complete();
            });
        });
    }

    @Test
    public void testCreateItemWithID(TestContext context) {
        Item input = Item.builder()
                ._id("this shouldn't be here")
                .name("test")
                .quantity(15)
                .unit("kg")
                .barcode("01024150")
                .bestBefore(LocalDate.parse("2007-10-12"))
                .notification(Notification.builder().on(LocalDate.parse("2010-05-12")).build())
                .build();
        Async async = context.async();
        service.createItem(input, res -> {
            context.assertFalse(res.succeeded());
            context.assertTrue(res.cause().getMessage().startsWith(ItemDao.ID_MUST_BE_NULL));
            async.complete();
        });
    }

    /**
     * Test of updateItem method, of class ItemServiceImpl.
     */
    @Test
    public void testUpdateItem(TestContext context) {
        String id = "#02";
        String newName = "newname, different than the previous";
        Async async = context.async();
        service.getItem(id, res -> {
            context.assertTrue(res.succeeded());
            Item expected = res.result();
            expected.setName(newName);
            service.updateItem(id, expected, res2 -> {
                context.assertTrue(res2.succeeded());
                service.getItem(id, res3 -> {
                    context.assertTrue(res3.succeeded());
                    Item test = res3.result();
                    context.assertEquals(newName, test.getName());
                    context.assertEquals(expected, test);
                    async.complete();
                });
            });
        });
    }

    /**
     * Test of removeItem method, of class ItemServiceImpl.
     */
    @Test
    public void testRemoveItem(TestContext context) {
        String id = "#03";
        Async async = context.async(2);
        service.getItem(id, res -> {
            context.assertTrue(res.succeeded());
            service.removeItem(id, res2 -> {
                context.assertTrue(res2.succeeded());
                service.getItem(id, res3 -> {
                    context.assertFalse(res3.succeeded());
                    async.countDown();
                });
                service.getItems(res4 -> {
                    context.assertTrue(res4.succeeded());
                    List<Item> items = res4.result();
                    items.stream().forEach((item) -> {
                        context.assertFalse(item.get_id().equals(id));
                    });
                    async.countDown();
                });
            });
        });
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
