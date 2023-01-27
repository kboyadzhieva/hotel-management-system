package com.moonlighthotel.hotelmanagementsystem.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomFilter {

    private final String startDate;

    private final String endDate;

    private final Integer adults;

    private final Integer kids;

    public boolean haveQueryParameters() {
        return startDate != null || endDate != null || adults != null || kids != null;
    }
}
