package com.github.inventorytracker.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Notification {
    @NonNull
    LocalDate on;
    boolean repeats;
    int repeatInterval;
    DateUnit unit;
}
