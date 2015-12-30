package com.github.inventorytracker.model;

public enum DateUnit {
    DAY, WEEK, MONTH;

    public static DateUnit fromString(String src){
        switch (src){
            case "DAY": return DAY;
            case "WEEK": return WEEK;
            case "MONTH": return MONTH;
            default: throw new IllegalArgumentException("Cannot recognize DateUnit: "+src);
        }
    }
}
