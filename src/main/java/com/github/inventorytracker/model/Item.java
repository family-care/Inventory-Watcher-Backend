package com.github.inventorytracker.model;

import com.github.inventorytracker.verticle.util.JsonUtil;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DataObject
public class Item implements JsonConvertable {
    @NonNull
    String name;
    int quantity;
    String unit;
    LocalDate bestBefore;
    @Singular
    List<String> tags;
    Notification notification;

    public Item(Item item){
        this(item.name, item.quantity, item.unit, item.bestBefore, item.tags, item.notification);
    }

    public Item(JsonObject json){
        this(JsonUtil.fromJson(json.encode(), Item.class));
    }
}
