package com.github.inventorytracker.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @NonNull
    LocalDate on;
    int repeatInterval;
    DateUnit unit;
}
