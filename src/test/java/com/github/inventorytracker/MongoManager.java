package com.github.inventorytracker;

import com.github.inventorytracker.dao.ItemDao;
import com.github.inventorytracker.model.Item;
import com.github.inventorytracker.model.JsonConvertable;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.vertx.core.Vertx;
import io.vertx.core.json.EncodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author pjozsef
 */
public class MongoManager {

    private MongodProcess MONGO;
    private int MONGO_PORT;
    private final Vertx vertx;
    private final List<? extends JsonConvertable> data;
    TestContext context;

    public MongoManager(Vertx vertx, int MONGO_PORT, TestContext context, List<? extends JsonConvertable> data) throws IOException {
        this.MONGO_PORT = MONGO_PORT;
        this.vertx = vertx;
        this.context = context;
        this.data = data;
        
        start();
        initDB();
    }

    private void start() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(MONGO_PORT, Network.localhostIsIPv6()))
                .build();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        MONGO = mongodExecutable.start();
    }

    private void initDB() throws EncodeException {
        Async async = context.async(data.size());

        JsonObject config = new JsonObject().put("host", "localhost").put("port", MONGO_PORT);
        MongoClient mongoClient = MongoClient.createShared(vertx, config);

        data.stream()
                .map((item) -> item.toJsonObject())
                .forEach((json) -> {
                    mongoClient.insert(
                            ItemDao.COLLECTION,
                            json,
                            res -> async.countDown());
                });
        async.await();
        mongoClient.close();
    }

    public void stop() {
        MONGO.stop();
    }
}
