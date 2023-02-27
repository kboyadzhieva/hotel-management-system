package com.moonlighthotel.hotelmanagementsystem.enumeration;

public enum SectionType {
    SMOKING(0), NONSMOKING(1);

    private final int value;

    SectionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
