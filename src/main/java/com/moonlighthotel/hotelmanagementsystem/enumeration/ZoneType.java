package com.moonlighthotel.hotelmanagementsystem.enumeration;

import java.util.Arrays;

public enum ZoneType {
    TERRACE(0), SALON(1), BAR(2);

    private final int value;

    ZoneType(int value) {
        this.value = value;
    }

    public static ZoneType valueOf(int value) {
        return Arrays.stream(values())
                .filter(zoneType -> zoneType.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
