package com.github.inventorytracker.model;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Notification {
    protected static String ON = "on", REPEAT_INTERVAL = "every", UNIT = "unit";
    @NonNull
    LocalDate on;
    int repeatInterval;
    DateUnit unit;

    public static Notification fromJson(JsonObject src) {
        NotificationBuilder builder = Notification.builder();
        builder.on(LocalDate.parse(src.getString(ON)));
        Integer repeatInterval = src.getInteger(REPEAT_INTERVAL);
        if (repeatInterval != null) {
            builder.repeatInterval(repeatInterval)
                    .unit(DateUnit.fromString(src.getString(UNIT)));
        }
        return builder.build();
    }

    public static JsonObject toJson(Notification src) {
        JsonObject result = new JsonObject();
        result.put(ON, src.getOn().toString());
        if (src.getRepeatInterval() > 0) {
            result.put(REPEAT_INTERVAL, src.getRepeatInterval());
            result.put(UNIT, src.getUnit().toString());
        }
        return result;
    }

    public JsonObject toJson() {
        return Notification.toJson(this);
    }
}
