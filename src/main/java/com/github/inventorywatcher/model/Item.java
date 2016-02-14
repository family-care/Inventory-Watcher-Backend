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
public class Item implements Validatable, JsonConvertable {

    public static final String NAME_MUST_NOT_BE_NULL = "Name must not be null!";

    static {
        Json.mapper.registerModule(new JavaTimeModule());
    }

    private String _id;
    private String name;
    private String barcode;
    private double quantity;
    private String unit;
    private LocalDate bestBefore;
    private List<String> tags;
    private Notification notification;

    public Item() {

    }

    public Item(String _id, String name, String barcode, double quantity, String unit, LocalDate bestBefore, List<String> tags, Notification notification) {
        this._id = _id;
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.unit = unit;
        this.bestBefore = bestBefore;
        this.tags = tags;
        this.notification = notification;
    }

    public Item(Item item) {
        this(item._id, item.name, item.barcode, item.quantity, item.unit, item.bestBefore, item.tags, item.notification);
    }

    public Item(JsonObject json) {
        this(Json.decodeValue(json.encode(), Item.class));
    }

    public Item(String json) {
        this(Json.decodeValue(json, Item.class));
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this._id);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.barcode);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.quantity) ^ (Double.doubleToLongBits(this.quantity) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.unit);
        hash = 67 * hash + Objects.hashCode(this.bestBefore);
        hash = 67 * hash + Objects.hashCode(this.tags);
        hash = 67 * hash + Objects.hashCode(this.notification);
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
        final Item other = (Item) obj;
        if (Double.doubleToLongBits(this.quantity) != Double.doubleToLongBits(other.quantity)) {
            return false;
        }
        if (!Objects.equals(this._id, other._id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.barcode, other.barcode)) {
            return false;
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        if (!Objects.equals(this.bestBefore, other.bestBefore)) {
            return false;
        }
        if (!Objects.equals(this.tags, other.tags)) {
            return false;
        }
        if (!Objects.equals(this.notification, other.notification)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "_id=" + _id + ", name=" + name + ", barcode=" + barcode + ", quantity=" + quantity + ", unit=" + unit + ", bestBefore=" + bestBefore + ", tags=" + tags + ", notification=" + notification + '}';
    }
    
    

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (name == null) {
            errors.add(NAME_MUST_NOT_BE_NULL);
        }
        return errors;
    }

    @Override
    public JsonObject toJson() {
        return new JsonObject(this.toJsonString());
    }
}
