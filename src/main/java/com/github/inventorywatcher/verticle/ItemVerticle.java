package com.github.inventorywatcher.verticle;


import com.github.inventorywatcher.service.ItemService;
import com.github.inventorywatcher.service.impl.ItemServiceImpl;
import com.github.inventorywatcher.util.EmbeddedMongo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;

public class ItemVerticle extends AbstractVerticle{
    private final Logger log = LoggerFactory.getLogger(DeployVerticle.class);
    private EmbeddedMongo mongo;

    @Override
    public void start(){
        mongo = new EmbeddedMongo(vertx, 2500, null, () -> {
            ItemService itemService = new ItemServiceImpl(
                    vertx,
                    new JsonObject().put("host", "localhost").put("port", 2500)
            );
            ProxyHelper.registerService(ItemService.class, vertx, itemService, ItemService.ADDRESS);
        });
    }

    @Override
    public void stop(){
        mongo.stop();
    }
}
