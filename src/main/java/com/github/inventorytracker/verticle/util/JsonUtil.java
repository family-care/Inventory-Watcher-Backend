package com.github.inventorytracker.verticle.util;

import io.vertx.core.json.JsonObject;

public class JsonUtil {
    public static void putIfValueNotNull(JsonObject json, String key, Object value) {
        if(value!=null){
            json.put(key, value);
        }
    }
}
