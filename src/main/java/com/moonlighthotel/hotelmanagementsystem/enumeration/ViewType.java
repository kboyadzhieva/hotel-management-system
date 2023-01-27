package com.moonlighthotel.hotelmanagementsystem.enumeration;

import java.util.Arrays;

public enum ViewType {
    SEA, GARDEN, POOL;

    public static ViewType findByName(String name) {
        return Arrays.stream(ViewType.values())
                .filter(view -> view.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
