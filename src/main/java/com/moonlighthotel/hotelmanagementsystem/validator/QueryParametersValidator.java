package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class QueryParametersValidator {

    @Autowired
    private final DateFormatter dateFormatter;

    @Autowired
    private final DateValidator dateValidator;

    public void validateQueryParameters(RoomFilter roomFilter) {
        Instant startDate = dateFormatter.stringToInstant(roomFilter.getStartDate());
        Instant endDate = dateFormatter.stringToInstant(roomFilter.getEndDate());

        dateValidator.validateDates(startDate, endDate);
    }

    public boolean AreThereQueryParameters(RoomFilter roomFilter) {
        return roomFilter.haveQueryParameters();
    }
}
