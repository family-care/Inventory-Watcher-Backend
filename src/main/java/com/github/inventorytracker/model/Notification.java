package com.github.inventorytracker.model;

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
public class Notification implements Validatable, JsonConvertable{
    public static final String ON_MUST_NOT_BE_NULL= "On must not be null";

    static {
        Json.mapper.registerModule(new JavaTimeModule());
    }
    
    LocalDate on;
    int repeatInterval;
    DateUnit unit;

    public Notification(Notification notification) {
        this(notification.on, notification.repeatInterval, notification.unit);
    }

    public Notification(JsonObject json) {
        this(Json.decodeValue(json.encode(), Notification.class));
    }
    
    public Notification(String json) {
        this(Json.decodeValue(json, Notification.class));
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if(on==null){
            errors.add(ON_MUST_NOT_BE_NULL);
        }
        return errors;
    }
}
