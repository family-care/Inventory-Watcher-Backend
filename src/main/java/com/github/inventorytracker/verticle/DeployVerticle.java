package com.github.inventorytracker.verticle;


import com.github.inventorytracker.verticle.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DeployVerticle extends AbstractVerticle{
    private final Logger log = LoggerFactory.getLogger(DeployVerticle.class);

    public static void main(String[] args) {
        Runner.runExample(DeployVerticle.class);
    }

    @Override
    public void start(){
    }
}