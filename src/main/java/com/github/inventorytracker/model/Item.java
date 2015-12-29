package com.github.inventorytracker.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class Item {
    @NonNull
    String name;
    int quantity;
    String unit;
    LocalDate bestBefore;
    @Singular
    List<String> tags;
    Notification notification;
}
