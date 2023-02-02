package com.moonlighthotel.hotelmanagementsystem.enumeration;

import java.util.Arrays;

public enum StatusType {
    NEW(0), ACCEPTED(1), CANCELED(2);

    private final int value;

    StatusType(int value) {
        this.value = value;
    }

    public static StatusType valueOf(int value) {
        return Arrays.stream(values())
                .filter(statusType -> statusType.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
