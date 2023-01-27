package com.moonlighthotel.hotelmanagementsystem.enumeration;

import java.util.Arrays;

public enum BedType {
    ONE, SEPARATE;

    public static BedType findByName(String name) {
        return Arrays.stream(BedType.values())
                .filter(bedType -> bedType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
