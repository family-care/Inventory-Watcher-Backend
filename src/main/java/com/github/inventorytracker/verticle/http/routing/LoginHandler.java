package com.github.inventorytracker.verticle.http.routing;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class LoginHandler {
    
    public Router router(Vertx vertx){
        final Router sub = Router.router(vertx);
        sub.route("/").handler(this::login);
        sub.route("/newpassword").handler(this::newPassword);
        return sub;
    }
    
    private void login(RoutingContext ctx){
        HttpServerResponse res = ctx.response();
        res.end("Logged in");
    }
    
    private void newPassword(RoutingContext ctx){
        HttpServerResponse res = ctx.response();
        res.end("New password created");
    }
}
