package com.github.inventorytracker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
public class Item {
    String _id;
    @NonNull
    String name;
    String barcode;
    double quantity;
    String unit;
    LocalDate bestBefore;
    @Singular
    List<String> tags;
    Notification notification;

    public Item(Item item) {
        this(item._id, item.name, item.barcode, item.quantity, item.unit, item.bestBefore, item.tags, item.notification);
    }

    public Item(JsonObject json) {
        this(Json.decodeValue(json.encode(), Item.class));
    }
}
