package com.github.inventorywatcher.model;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * This interface act as a trait, enabling a class to be JSON convertable.
 * @author pjozsef
 */
public interface JsonConvertable {
    default String toJson(){
        return Json.encode(this);
    };
    
    default JsonObject toJsonObject(){
        return new JsonObject(this.toJson());
    }
}
