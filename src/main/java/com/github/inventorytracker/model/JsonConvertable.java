package com.github.inventorytracker.model;

import com.github.inventorytracker.verticle.util.JsonUtil;
import io.vertx.core.json.JsonObject;

public interface JsonConvertable {
    default JsonObject toJson(){
        return new JsonObject(JsonUtil.toJson(this));
    }
}
