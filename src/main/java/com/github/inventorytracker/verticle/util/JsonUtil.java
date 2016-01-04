package com.github.inventorytracker.verticle.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object o) {
        String result;
        try {
            result = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }

    public static <T> T fromJson(String source, Class<T> type) {
        T result;
        try {
            result = mapper.readValue(source, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }
}
