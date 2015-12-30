package com.github.inventorytracker.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

import static com.github.inventorytracker.verticle.util.JsonUtil.putIfValueNotNull;

@Value
@Builder
public class Item {
    protected static String
            NAME = "name",
            QUANTITY = "quantity",
            UNIT = "unit",
            BEST_BEFORE = "bestBefore",
            TAGS = "tags",
            NOTIFICATION = "notification";
    @NonNull
    String name;
    int quantity;
    String unit;
    LocalDate bestBefore;
    @Singular
    List<String> tags;
    Notification notification;

    public static Item fromJson(JsonObject src) {
        ItemBuilder builder = Item.builder();
        builder.name(src.getString(NAME))
                .quantity((src.getInteger(QUANTITY)))
                .unit(src.getString(UNIT));
        if(src.getString(BEST_BEFORE)!=null){
            builder.bestBefore(LocalDate.parse(src.getString(BEST_BEFORE)));
        }
        if(src.getJsonArray(TAGS)!=null){
            builder.tags(src.getJsonArray(TAGS).getList());
        }
        if(src.getJsonObject(NOTIFICATION)!=null){
            builder.notification(Notification.fromJson(src.getJsonObject(NOTIFICATION)));
        }
        return builder.build();
    }

    public static JsonObject toJson(Item src) {
        JsonObject result = new JsonObject();
        result.put(NAME, src.name);
        result.put(QUANTITY, src.quantity);
        putIfValueNotNull(result, UNIT, src.getUnit());
        if (src.bestBefore != null) {
            result.put(BEST_BEFORE, src.getBestBefore().toString());
        }
        result.put(TAGS, src.getTags());
        if(src.getTags()==null){
            result.put(TAGS, new JsonArray());
        }
        if (src.getNotification() != null) {
            result.put(NOTIFICATION, src.getNotification().toJson());
        }
        return result;
    }

    public JsonObject toJson() {
        return Item.toJson(this);
    }
}
