package com.github.inventorytracker.model;

import io.vertx.core.json.Json;

/**
 *
 * @author pjozsef
 */
public interface JsonConvertable {
    default String toJson(){
        return Json.encode(this);
    };
}
