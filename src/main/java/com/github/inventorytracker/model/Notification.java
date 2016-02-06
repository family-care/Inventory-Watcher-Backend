package com.github.inventorytracker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DataObject
@JsonInclude(Include.NON_NULL)
public class Notification {

    @NonNull
    LocalDate on;
    int repeatInterval;
    DateUnit unit;

    public Notification(Notification notification) {
        this(notification.on, notification.repeatInterval, notification.unit);
    }

    public Notification(JsonObject json) {
        this(Json.decodeValue(json.encode(), Notification.class));
    }
}
