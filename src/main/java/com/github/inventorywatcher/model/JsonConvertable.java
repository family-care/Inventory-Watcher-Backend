package com.github.inventorywatcher.model;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * This interface act as a trait, enabling a class to be JSON convertable.
 *
 * @author pjozsef
 */
public interface JsonConvertable {
    default String toJsonString() {
        return Json.encode(this);
    }

    default JsonObject toJson() {
        return new JsonObject(this.toJsonString());
    }
}
