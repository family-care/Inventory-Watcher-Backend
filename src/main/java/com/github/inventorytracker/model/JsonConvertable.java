package com.github.inventorytracker.model;

import io.vertx.core.json.Json;

/**
 * This interface act as a trait, enabling a class to be JSON convertable.
 * @author pjozsef
 */
public interface JsonConvertable {
    default String toJson(){
        return Json.encode(this);
    };
}
