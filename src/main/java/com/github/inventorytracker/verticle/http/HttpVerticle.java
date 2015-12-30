package com.github.inventorytracker.verticle.http;

import com.github.inventorytracker.verticle.http.routing.FrontendHandler;
import com.github.inventorytracker.verticle.http.routing.LoginHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class HttpVerticle extends AbstractVerticle{
    String webRoot;
    String host;
    int port;
    boolean shouldCache;
    
    @Override
    public void start() throws Exception {
        initConfig();
        
        HttpServer server = vertx.createHttpServer();
        initServer(server);
        server.listen(port, host);
    }

    private void initServer(HttpServer server) {
        Router router = Router.router(vertx);
        
        router.mountSubRouter("/login", new LoginHandler().router(vertx));
        router.mountSubRouter("/", new FrontendHandler().router(vertx));
        
        router.route().handler(StaticHandler.create().setCachingEnabled(shouldCache).setWebRoot(webRoot));
        server.requestHandler(router::accept);
    }

    private void initConfig() {
        webRoot = config().getString("webroot", "public");
        host = config().getString("host", "localhost");
        port = config().getInteger("port", 8080);
        shouldCache = config().getBoolean("shouldCache", false);
    }    
    
}
