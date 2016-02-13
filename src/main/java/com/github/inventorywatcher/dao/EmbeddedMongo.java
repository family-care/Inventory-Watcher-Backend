package com.github.inventorywatcher.dao;

import com.github.inventorywatcher.model.JsonConvertable;
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
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author pjozsef
 */
public class EmbeddedMongo {

    private final Vertx vertx;
    private final List<? extends JsonConvertable> data;
    private MongodProcess MONGO;
    private int MONGO_PORT;

    public EmbeddedMongo(Vertx vertx, int MONGO_PORT, List<? extends JsonConvertable> data, Runnable callback) {
        this.MONGO_PORT = MONGO_PORT;
        this.vertx = vertx;
        this.data = data;

        try {
            start();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize embedded mongoDB");
        }
        if (data != null && data.size() > 0) {
            initDB();
        }
        callback.run();
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
        CountDownLatch cdl = new CountDownLatch(data.size());

        JsonObject config = new JsonObject().put("host", "localhost").put("port", MONGO_PORT);
        MongoClient mongoClient = MongoClient.createShared(vertx, config);

        try {
            data.stream()
                    .map(JsonConvertable::toJsonObject)
                    .forEach((json) -> mongoClient.insert(
                            ItemDao.COLLECTION,
                            json,
                            res -> cdl.countDown()));

            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    public void stop() {
        MONGO.stop();
    }
}
