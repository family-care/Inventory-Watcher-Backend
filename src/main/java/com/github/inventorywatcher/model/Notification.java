package com.github.inventorywatcher.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DataObject
@JsonInclude(Include.NON_NULL)
public class Notification implements Validatable, JsonConvertable {

    public static final String ON_MUST_NOT_BE_NULL = "On must not be null";

    static {
        Json.mapper.registerModule(new JavaTimeModule());
    }

    private LocalDate on;
    private int repeatInterval;
    private DateUnit unit;

    public Notification() {
    }

    public Notification(LocalDate on, int repeatInterval, DateUnit unit) {
        this.on = on;
        this.repeatInterval = repeatInterval;
        this.unit = unit;
    }

    public Notification(Notification notification) {
        this(notification.on, notification.repeatInterval, notification.unit);
    }

    public Notification(JsonObject json) {
        this(Json.decodeValue(json.encode(), Notification.class));
    }

    public Notification(String json) {
        this(Json.decodeValue(json, Notification.class));
    }

    public LocalDate getOn() {
        return on;
    }

    public void setOn(LocalDate on) {
        this.on = on;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public DateUnit getUnit() {
        return unit;
    }

    public void setUnit(DateUnit unit) {
        this.unit = unit;
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (on == null) {
            errors.add(ON_MUST_NOT_BE_NULL);
        }
        return errors;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.on);
        hash = 17 * hash + this.repeatInterval;
        hash = 17 * hash + Objects.hashCode(this.unit);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notification other = (Notification) obj;
        if (this.repeatInterval != other.repeatInterval) {
            return false;
        }
        if (!Objects.equals(this.on, other.on)) {
            return false;
        }
        if (this.unit != other.unit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notification{" + "on=" + on + ", repeatInterval=" + repeatInterval + ", unit=" + unit + '}';
    }
    
    public JsonObject toJson() {
        return new JsonObject(this.toJsonString());
    }
}
