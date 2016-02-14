package com.github.inventorywatcher.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DataObject
@JsonInclude(Include.NON_NULL)
public class Item implements Validatable, JsonConvertable{
    public static final String NAME_MUST_NOT_BE_NULL = "Name must not be null!";

    static {
        Json.mapper.registerModule(new JavaTimeModule());
    }
    
    String _id;
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
    
    public Item(String json){
        this(Json.decodeValue(json, Item.class));
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if(name==null){
            errors.add(NAME_MUST_NOT_BE_NULL);
        }
        return errors;
    }
}
