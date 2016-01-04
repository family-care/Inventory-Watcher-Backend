package com.github.inventorytracker.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
