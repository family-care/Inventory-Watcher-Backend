package com.github.inventorywatcher.verticle.http;

import com.github.inventorywatcher.verticle.http.route.ItemRouterV1;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class HttpVerticle extends AbstractVerticle {
    private static final String API_V1 = "/api/v1/";
    String webRoot;
    String host;
    int port;
    boolean shouldCache;

    private void initConfig() {
        webRoot = config().getString("webroot", "public");
        host = config().getString("host", "localhost");
        port = config().getInteger("port", 8080);
        shouldCache = config().getBoolean("shouldCache", true);
    }

    @Override
    public void start() {
        initConfig();
        httpServer().listen(port, host);
    }

    private HttpServer httpServer() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        //Cookie handlers, auth handlers etc will come here

        //API routers here
        router.mountSubRouter(API_V1, new ItemRouterV1(vertx).get());

        //Static handler will come here
        //Error handler will come here as well with router.route().last()

        server.requestHandler(router::accept);
        return server;
    }


}
