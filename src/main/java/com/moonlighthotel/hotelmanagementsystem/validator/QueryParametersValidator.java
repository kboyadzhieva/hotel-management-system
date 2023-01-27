package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@Component
public class QueryParametersValidator {

    @Autowired
    private final DateFormatter dateFormatter;

    private static final String END_DATE = "end_date";
    private static final String START_DATE = "start_date";

    public void validateQueryParameters(RoomFilter roomFilter) {
        Instant startDate = dateFormatter.stringToInstant(roomFilter.getStartDate());
        Instant endDate = dateFormatter.stringToInstant(roomFilter.getEndDate());

        validateDates(startDate, endDate);
    }

    private void validateDates(Instant startDate, Instant endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidRequestException("The end date cannot be before the start date.", END_DATE);
        } else if (startDate.isBefore(Instant.now()) ||
                (endDate.isBefore(Instant.now()))) {
            throw new InvalidRequestException(
                    String.format("The start date and the end date cannot be before '%s'.", LocalDate.now()),
                    START_DATE + " " + END_DATE);
        }
        if (startDate.equals(endDate)) {
            throw new InvalidRequestException("The end date cannot match the start date.", END_DATE);
        }
    }
}
