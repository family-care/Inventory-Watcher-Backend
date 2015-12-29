package com.github.budgettracker.vertx;

import com.github.budgettracker.vertx.http.HttpVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DeployVerticle extends AbstractVerticle{
    private final Logger log = LoggerFactory.getLogger(DeployVerticle.class);

    @Override
    public void start(){
        vertx.deployVerticle(new HttpVerticle());
        log.info("Whatever");
    }
}
