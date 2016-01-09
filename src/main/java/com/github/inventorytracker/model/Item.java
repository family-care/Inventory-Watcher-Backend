package com.github.inventorytracker.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DataObject
public class Item {
    @NonNull
    String name;
    String barcode;
    int quantity;
    String unit;
    LocalDate bestBefore;
    @Singular
    List<String> tags;
    Notification notification;

    public Item(Item item) {
        this(item.name, item.barcode, item.quantity, item.unit, item.bestBefore, item.tags, item.notification);
    }

    public Item(JsonObject json) {
        this(Json.decodeValue(json.encode(), Item.class));
    }
}
