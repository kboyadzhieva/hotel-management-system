package com.moonlighthotel.hotelmanagementsystem.enumeration;

import java.util.Arrays;

public enum SectionType {
    SMOKING(0), NONSMOKING(1);

    private final int value;

    SectionType(int value) {
        this.value = value;
    }

    public static SectionType valueOf(int value) {
        return Arrays.stream(values())
                .filter(sectionType -> sectionType.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
